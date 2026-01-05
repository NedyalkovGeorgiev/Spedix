package org.informatics.service;

import static org.informatics.configuration.TransportConfig.LEGAL_HEIGHT_LIMIT;
import static org.informatics.configuration.TransportConfig.LEGAL_LENGTH_LIMIT;
import static org.informatics.configuration.TransportConfig.LEGAL_WIDTH_LIMIT;

import org.informatics.dao.CompanyDao;
import org.informatics.dao.DriverDao;
import org.informatics.dao.TransportDao;
import org.informatics.dto.TransportDTO;
import org.informatics.entity.Bus;
import org.informatics.entity.CargoTransport;
import org.informatics.entity.Company;
import org.informatics.entity.Driver;
import org.informatics.entity.PassengerTransport;
import org.informatics.entity.Qualification;
import org.informatics.entity.RefrigeratedTruck;
import org.informatics.entity.Transport;
import org.informatics.entity.Truck;
import org.informatics.validator.EntityValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransportService {
    private final TransportDao transportDao = new TransportDao();

    public void logTransport(Transport transport) {
        validateBusinessRules(transport);
        transportDao.create(transport);
    }

    public void validateBusinessRules(Transport transport) {
        EntityValidator.validate(transport);

        if (transport.getDriver() == null || transport.getVehicle() == null || transport.getClient() == null) {
            throw new IllegalArgumentException("Transport must have a Driver, Vehicle, and Client assigned!");
        }

        if (transport.getArrivalDate().isBefore(transport.getDepartureDate())) {
            throw new IllegalArgumentException("Arrival cannot be before Departure!");
        }

        Driver driverWithQuals = new DriverDao().getDriverWithQualifications(transport.getDriver().getId());

        if (transport instanceof CargoTransport cargo) {
            if (!(transport.getVehicle() instanceof Truck truck)) {
                throw new IllegalArgumentException("Cargo transport requires a Truck type vehicle!");
            }

            if (cargo.getWeight() > truck.getMaxLoadWeight()) {
                throw new IllegalArgumentException("Cargo weight exceeds truck capacity!");
            }

            if (truck instanceof RefrigeratedTruck reefer && (cargo.getRequiredMinTemp() != null ||
                cargo.getRequiredMaxTemp() != null)) {
                if (cargo.getRequiredMinTemp() != null && cargo.getRequiredMinTemp() < reefer.getMinTemperature()) {
                    throw new IllegalArgumentException("Truck cannot reach required minimum temperature!");
                }

                if (cargo.getRequiredMaxTemp() != null && cargo.getRequiredMaxTemp() > reefer.getMaxTemperature()) {
                    throw new IllegalArgumentException("Truck cannot maintain required maximum temperature!");
                }
            }

            if (cargo.isHazardous() && !driverWithQuals.getQualifications().contains(Qualification.HAZMAT)) {
                throw new IllegalArgumentException("Driver not qualified for Hazardous Cargo!");
            }

            if ((cargo.getHeight() != null && cargo.getHeight() > LEGAL_HEIGHT_LIMIT) ||
                    (cargo.getWidth() != null && cargo.getWidth() > LEGAL_WIDTH_LIMIT) ||
                    (cargo.getLength() != null && cargo.getLength() > LEGAL_LENGTH_LIMIT)) {
                cargo.setOversized(true);
            }

            if ((cargo.isOversized() && !driverWithQuals.getQualifications().contains(Qualification.OVERSIZED))) {
                throw new IllegalArgumentException("Driver not qualified for Oversized Cargo!");
            }
        } else if (transport instanceof PassengerTransport passenger) {
            if (transport.getVehicle() instanceof Bus bus) {
                if (passenger.getPassengerCount() > bus.getMaxPassengers()) {
                    throw new IllegalArgumentException("Passenger count exceeds bus capacity!");
                }
            } else {
                throw new IllegalArgumentException("Passenger transport requires a Bus!");
            }
        }
    }

    public void markAsPaid(Long transportId) {
        Transport transport = transportDao.getById(transportId);

        if (transport != null && !transport.isPaid()) {
            transport.setPaid(true);
            transportDao.update(transport);

            if (transport.getCompany() != null) {
                CompanyDao companyDao = new CompanyDao();
                Company company = companyDao.getById(transport.getCompany().getId());

                if (company != null) {
                    BigDecimal price = transport.getPrice() != null ? transport.getPrice() : BigDecimal.ZERO;
                    BigDecimal currentRevenue = company.getTotalRevenue() != null ? company.getTotalRevenue() : BigDecimal.ZERO;

                    company.setTotalRevenue(currentRevenue.add(price));
                    companyDao.update(company);
                }
            }
        }
    }

    public List<TransportDTO> getTransports() {
        return transportDao.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Transport> getAllTransportsByDestination(String destination) {
        return transportDao.getByDestination(destination);
    }

    public Long getCountOfCompletedTransportsForDriver(Long driverId) {
        return transportDao.getTransportCountForDriver(driverId);
    }

    public BigDecimal getTotalRevenueForDriver(Long driverId) {
        return transportDao.getRevenueForDriver(driverId);
    }

    public BigDecimal getTotalRevenueBySpecificPeriod(LocalDateTime start, LocalDateTime end) {
        return transportDao.getTotalRevenueByPeriod(start, end);
    }

    public TransportDTO convertToDTO(Transport transport) {
        return TransportDTO.builder().id(transport.getId())
                .startPoint(transport.getStartPoint())
                .endPoint(transport.getEndPoint())
                .departureDate(transport.getDepartureDate())
                .price(transport.getPrice())
                .driverName(transport.getDriver() != null ? transport.getDriver().getName() : "N/A")
                .clientName(transport.getClient() != null ? transport.getClient().getName() : "N/A")
                .vehicleInfo(transport.getVehicle() != null ? transport.getVehicle().getMake() + " " + transport.getVehicle().getModel() : "N/A")
                .build();
    }

    public List<TransportDTO> getAllTransportsDTO() {
        return transportDao.getAllWithDetails().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

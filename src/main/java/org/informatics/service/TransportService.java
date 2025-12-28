package org.informatics.service;

import org.informatics.dao.TransportDao;
import org.informatics.entity.Bus;
import org.informatics.entity.CargoTransport;
import org.informatics.entity.ConcreteMixer;
import org.informatics.entity.PassengerTransport;
import org.informatics.entity.Qualification;
import org.informatics.entity.RefrigeratedTruck;
import org.informatics.entity.Tanker;
import org.informatics.entity.Transport;
import org.informatics.entity.Truck;

import java.util.List;

public class TransportService {
    private final TransportDao transportDao = new TransportDao();

    public void logTransport(Transport transport) {
        if (transport.getDriver() == null || transport.getVehicle() == null || transport.getClient() == null) {
            throw new IllegalArgumentException("Transport must have a Driver, Vehicle, and Client assigned!");
        }

        if (transport.getArrivalDate().isBefore(transport.getDepartureDate())) {
            throw new IllegalArgumentException("Arrival cannot be before Departure");
        }

        if (transport instanceof CargoTransport cargo) {
            if (transport.getVehicle() instanceof Truck truck) {
                if (cargo.getWeight() > truck.getMaxLoadWeight()) {
                    throw new IllegalArgumentException("Cargo weight exceeds truck capacity!");
                }
            } else {
                throw new IllegalArgumentException("Cargo transport requires a Truck type vehicle!");
            }

            if (cargo.isHazardous() && !transport.getDriver().getQualifications().contains(Qualification.HAZMAT)) {
                throw new IllegalArgumentException("Driver is not qualified for Hazardous Cargo!");
            }

            if (cargo.isOversized() && !transport.getDriver().getQualifications().contains(Qualification.OVERSIZED)) {
                throw new IllegalArgumentException("Driver is not qualified for Oversized Cargo!");
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

        transportDao.create(transport);
    }

    public void markAsPaid(Long transportId) {
        Transport transport = transportDao.getById(transportId);
        if (transport != null) {
            transport.setPaid(true);
            transportDao.update(transport);
        }
    }

    public List<Transport> getTransports() {
        return transportDao.getAll();
    }
}

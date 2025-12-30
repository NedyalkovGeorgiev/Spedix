package org.informatics.service;

import org.informatics.dao.VehicleDao;
import org.informatics.dto.VehicleDTO;
import org.informatics.entity.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleService {
    private final VehicleDao vehicleDao = new VehicleDao();

    public void createVehicle(Vehicle vehicle) {
        vehicleDao.create(vehicle);
    }

    public VehicleDTO convertToDTO(Vehicle vehicle) {
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .type(vehicle.getClass().getSimpleName())
                .companyName(vehicle.getCompany() != null ? vehicle.getCompany().getName() : "N/A")
                .build();
    }

    public List<VehicleDTO> getVehicles() {
        return vehicleDao.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

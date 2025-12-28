package org.informatics.service;

import org.informatics.dao.VehicleDao;
import org.informatics.entity.Vehicle;

import java.util.List;

public class VehicleService {
    private final VehicleDao vehicleDao = new VehicleDao();

    public void createVehicle(Vehicle vehicle) {
        vehicleDao.create(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicleDao.getAll();
    }
}

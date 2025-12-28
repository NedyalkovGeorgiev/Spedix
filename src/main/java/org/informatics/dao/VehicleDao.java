package org.informatics.dao;

import org.informatics.entity.Vehicle;

public class VehicleDao extends GenericDao<Vehicle> {
    public VehicleDao() {
        super(Vehicle.class);
    }
}

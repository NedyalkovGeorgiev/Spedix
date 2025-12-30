package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Vehicle;

import java.util.List;

public class VehicleDao extends GenericDao<Vehicle> {
    public VehicleDao() {
        super(Vehicle.class);
    }

    public List<Vehicle> getAllWithCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT v FROM Vehicle v LEFT JOIN FETCH v.company", Vehicle.class)
                    .getResultList();
        }
    }
}

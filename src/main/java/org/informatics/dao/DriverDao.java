package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Driver;

public class DriverDao extends GenericDao<Driver> {
    public DriverDao() {
        super(Driver.class);
    }

    public Driver getDriverWithQualifications(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT d FROM Driver d LEFT JOIN FETCH d.qualifications WHERE d.id = :id", Driver.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }
}

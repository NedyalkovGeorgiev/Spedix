package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Driver;
import org.informatics.entity.Qualification;

import java.util.List;

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

    public List<Driver> getAllDriversWithDetails() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT d FROM Driver d " +
                                    "LEFT JOIN FETCH d.company " +
                                    "LEFT JOIN FETCH d.qualifications", Driver.class)
                    .getResultList();
        }
    }

    public List<Driver> getAllSortedBySalary() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT d FROM Driver d " +
                    "LEFT JOIN FETCH d.company " +
                    "LEFT JOIN FETCH d.qualifications " +
                    "ORDER BY d.salary DESC", Driver.class).getResultList();
        }
    }

    public List<Driver> getDriversByQualification(Qualification q) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT d FROM Driver d " +
                            "JOIN FETCH d.qualifications qList " +
                            "LEFT JOIN FETCH d.company " +
                            "WHERE qList = :q", Driver.class)
                    .setParameter("q", q)
                    .getResultList();
        }
    }
}

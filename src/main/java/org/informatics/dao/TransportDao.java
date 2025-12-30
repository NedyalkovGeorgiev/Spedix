package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Transport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TransportDao extends GenericDao<Transport> {
    public TransportDao() {
        super(Transport.class);
    }

    public List<Transport> getByDestination(String destination) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transport t WHERE t.endPoint = :dest", Transport.class)
                    .setParameter("dest", destination)
                    .getResultList();
        }
    }

    public Long getTransportCountForDriver(Long driverId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(t) FROM Transport t where t.driver.id = :id", Long.class)
                    .setParameter("id", driverId)
                    .getSingleResult();
        }
    }

    public BigDecimal getRevenueForDriver(Long driverId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            BigDecimal revenue = session.createQuery("SELECT SUM(t.price) FROM Transport t WHERE t.driver.id = :id", BigDecimal.class)
                    .setParameter("id", driverId)
                    .getSingleResult();
            return Objects.requireNonNullElse(revenue, BigDecimal.ZERO);
        }
    }

    public BigDecimal getTotalRevenueByPeriod(LocalDateTime start, LocalDateTime end) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            BigDecimal revenue = session.createQuery(
                    "SELECT SUM(t.price) FROM Transport t WHERE t.departureDate BETWEEN :start and :end", BigDecimal.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();
            return Objects.requireNonNullElse(revenue, BigDecimal.ZERO);
        }
    }

    public List<Transport> getAllWithDetails() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT t FROM Transport t " +
                            "LEFT JOIN FETCH t.driver " +
                            "LEFT JOIN FETCH t.client " +
                            "LEFT JOIN FETCH t.vehicle", Transport.class)
                    .getResultList();
        }
    }
}

package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Company;

import java.util.List;

public class CompanyDao extends GenericDao<Company> {

    public CompanyDao() {
        super(Company.class);
    }

    public List<Company> getAllSortedByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees ORDER BY c.name ASC",
                            Company.class).getResultList();
        }
    }

    public List<Company> getAllSortedByRevenue() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees ORDER BY c.totalRevenue DESC",
                            Company.class).getResultList();
        }
    }

    public List<Company> getAllWithEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees",
                    Company.class).getResultList();
        }
    }
}

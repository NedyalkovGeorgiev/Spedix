package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Employee;
import org.informatics.entity.Qualification;

import java.util.List;

public class EmployeeDao extends GenericDao<Employee> {
    public EmployeeDao() {
        super(Employee.class);
    }

    public List<Employee> getAllSortedBySalary() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee e ORDER BY e.salary DESC", Employee.class).getResultList();
        }
    }

    public List<Employee> getDriversByQualification(Qualification q) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT d FROM Driver d JOIN d.qualifications " +
                    "qList WHERE qList = :q", Employee.class)
                    .setParameter("q", q)
                    .getResultList();
        }
    }
}

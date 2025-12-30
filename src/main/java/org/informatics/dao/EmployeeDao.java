package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Employee;

import java.util.List;

public class EmployeeDao extends GenericDao<Employee> {
    public EmployeeDao() {
        super(Employee.class);
    }

    public List<Employee> getAllWithCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM Employee e LEFT JOIN FETCH e.company", Employee.class)
                    .getResultList();
        }
    }
}

package org.informatics.dao;

import org.informatics.entity.Employee;

public class EmployeeDao extends GenericDao<Employee> {
    public EmployeeDao() {
        super(Employee.class);
    }
}

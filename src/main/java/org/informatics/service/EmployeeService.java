package org.informatics.service;

import org.informatics.dao.EmployeeDao;
import org.informatics.entity.Employee;

import java.util.List;

public class EmployeeService {
    private final EmployeeDao employeeDao = new EmployeeDao();

    public void createEmployee(Employee employee) {
        employeeDao.create(employee);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getAll();
    }
}

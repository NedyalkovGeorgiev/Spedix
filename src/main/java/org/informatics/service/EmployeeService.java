package org.informatics.service;

import org.informatics.dao.EmployeeDao;
import org.informatics.entity.Employee;
import org.informatics.entity.Qualification;

import java.util.List;

public class EmployeeService {
    private final EmployeeDao employeeDao = new EmployeeDao();

    public void createEmployee(Employee employee) {
        employeeDao.create(employee);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getAll();
    }

    public List<Employee> getAllEmployeesSortedBySalary() {
        return employeeDao.getAllSortedBySalary();
    }

    public List<Employee> getAllDriversBySpecificQualification(Qualification q) {
        return employeeDao.getDriversByQualification(q);
    }
}

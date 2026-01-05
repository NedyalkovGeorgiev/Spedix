package org.informatics.service;

import org.informatics.dao.DriverDao;
import org.informatics.dao.EmployeeDao;
import org.informatics.dto.DriverDTO;
import org.informatics.dto.EmployeeDTO;
import org.informatics.entity.Driver;
import org.informatics.entity.Employee;
import org.informatics.entity.Qualification;
import org.informatics.validator.EntityValidator;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private final DriverDao driverDao = new DriverDao();

    private final EmployeeDao employeeDao = new EmployeeDao();

    public void createEmployee(Employee employee) {
        EntityValidator.validate(employee);
        employeeDao.create(employee);
    }

    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .companyName(employee.getCompany() != null ? employee.getCompany().getName() : "N/A")
                .build();
    }

    public DriverDTO convertToDriverDTO(Driver driver) {
        return DriverDTO.builder()
                .id(driver.getId())
                .name(driver.getName())
                .salary(driver.getSalary())
                .companyName(driver.getCompany() != null ? driver.getCompany().getName() : "N/A")
                .qualifications(driver.getQualifications().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .build();
    }

    public List<DriverDTO> getDriversDTO() {
        return driverDao.getAllDriversWithDetails().stream()
                .map(this::convertToDriverDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getAllEmployeesSortedBySalary() {
        return employeeDao.getAllWithCompany().stream()
                .map(this::convertToEmployeeDTO)
                .sorted((e1, e2) -> e2.getSalary().compareTo(e1.getSalary()))
                .collect(Collectors.toList());
    }

    public List<DriverDTO> getAllDriversBySpecificQualification(Qualification q) {
        return driverDao.getDriversByQualification(q).stream()
                .map(this::convertToDriverDTO)
                .collect(Collectors.toList());
    }
}

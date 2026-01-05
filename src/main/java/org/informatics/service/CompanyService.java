package org.informatics.service;

import org.informatics.dao.CompanyDao;
import org.informatics.dto.CompanyDTO;
import org.informatics.entity.Company;
import org.informatics.validator.EntityValidator;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    private final CompanyDao companyDao = new CompanyDao();

    public void createCompany(Company company) {
        EntityValidator.validate(company);
        companyDao.create(company);
    }

    public List<CompanyDTO> getAllCompaniesSortedByName() {
        return companyDao.getAllSortedByName().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CompanyDTO> getAllCompaniesSortedByRevenue() {
        return companyDao.getAllSortedByRevenue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Company getCompanyById(Long id) {
        return companyDao.getById(id);
    }

    public List<CompanyDTO> getCompanies() {
        return companyDao.getAllWithEmployees().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO convertToDTO(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .address((company.getAddress()))
                .revenue(company.getTotalRevenue())
                .employeeCount(company.getEmployees() != null ? company.getEmployees().size() : 0)
                .build();
    }
}

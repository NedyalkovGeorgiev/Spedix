package org.informatics.service;

import org.informatics.dao.CompanyDao;
import org.informatics.entity.Company;

import java.util.List;

public class CompanyService {
    private final CompanyDao companyDao = new CompanyDao();

    public void createCompany(Company company) {
        companyDao.create(company);
    }

    public List<Company> getAllCompaniesSortedByName() {
        return companyDao.getAllSortedByName();
    }

    public List<Company> getAllCompaniesSortedByRevenue() {
        return companyDao.getAllSortedByRevenue();
    }

    public Company getCompanyById(Long id) {
        return companyDao.getById(id);
    }

    public List<Company> getCompanies() {
        return companyDao.getAll();
    }
}

package org.informatics.service;

import org.informatics.dao.CompanyDao;
import org.informatics.entity.Company;

import java.util.List;

public class CompanyService {
    private final CompanyDao companyDao = new CompanyDao();

    public void createCompany(Company company) {
        companyDao.create(company);
    }

    public List<Company> getCompanies() {
        return companyDao.getAll();
    }
}

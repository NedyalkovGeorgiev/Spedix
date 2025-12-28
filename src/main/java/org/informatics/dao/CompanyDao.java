package org.informatics.dao;

import org.informatics.entity.Company;

public class CompanyDao extends GenericDao<Company> {

    public CompanyDao() {
        super(Company.class);
    }
}

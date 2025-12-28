package org.informatics.service;

import org.informatics.dao.ClientDao;
import org.informatics.entity.Client;

import java.util.List;

public class ClientService {
    private final ClientDao clientDao = new ClientDao();

    public void createCompany(Client client) {
        clientDao.create(client);
    }

    public List<Client> getCompanies() {
        return clientDao.getAll();
    }
}

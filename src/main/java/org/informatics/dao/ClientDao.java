package org.informatics.dao;

import org.informatics.entity.Client;

public class ClientDao extends GenericDao<Client> {
    public ClientDao() {
        super(Client.class);
    }
}

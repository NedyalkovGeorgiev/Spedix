package org.informatics.service;

import org.informatics.dao.ClientDao;
import org.informatics.dto.ClientDTO;
import org.informatics.entity.Client;
import org.informatics.validator.EntityValidator;

import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private final ClientDao clientDao = new ClientDao();

    public void createClient(Client client) {
        EntityValidator.validate(client);
        clientDao.create(client);
    }

    public ClientDTO convertToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .build();
    }

    public List<ClientDTO> getClients() {
        return clientDao.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

package com.banshee.core.service;

import com.banshee.core.controller.exceptions.ClientIdNotFoundException;
import com.banshee.core.entity.Client;
import com.banshee.core.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }

    public Client createClient(Client client) {
        return clientRepository
                .save(client);
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clientRepository.findAll());
    }

    public Client updateClient(Client client, long id){
        Client retrievedClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientIdNotFoundException(id));
        retrievedClient.setNit(client.getNit());
        retrievedClient.setFullName(client.getFullName());
        retrievedClient.setAddress(client.getAddress());
        retrievedClient.setPhone(client.getPhone());
        retrievedClient.setCreditLimit(client.getCreditLimit());
        retrievedClient.setAvailableCredit(client.getAvailableCredit());
        retrievedClient.setVisitsPercentage(client.getVisitsPercentage());
        return clientRepository.save(retrievedClient);
    }
}

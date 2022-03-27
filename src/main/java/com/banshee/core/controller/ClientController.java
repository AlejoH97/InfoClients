package com.banshee.core.controller;

import com.banshee.core.controller.exceptions.ClientIdNotFoundException;
import com.banshee.core.entity.Client;
import com.banshee.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAllClients();
            if (clients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            return new ResponseEntity<>(
                    clientService.createClient(client), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClientById(@PathVariable("id") long id) {
        try {
            clientService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        try {
            return new ResponseEntity<>(clientService.updateClient(client, id), HttpStatus.OK);
        } catch (ClientIdNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

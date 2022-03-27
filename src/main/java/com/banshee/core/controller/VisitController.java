package com.banshee.core.controller;

import com.banshee.core.entity.Visit;
import com.banshee.core.repository.ClientRepository;
import com.banshee.core.repository.SalesRepresentativeRepository;
import com.banshee.core.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class VisitController {
    @Autowired
    VisitRepository visitRepository;

    @Autowired
    SalesRepresentativeRepository salesRepresentativeRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/visits")
    public ResponseEntity<List<Visit>> getAllVisits() {
        try {
            List<Visit> visits = new ArrayList<>(visitRepository.findAll());

            if (visits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(visits, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/visits/{clientId}")
    public ResponseEntity<Visit> createVisit(@PathVariable(value = "clientId") Long clientId,
                                                   @RequestBody Visit visit) {
        try {
            Visit newVisit = clientRepository.findById(clientId).map(client -> {
                client.addVisit(visit);
                return visitRepository.save(visit);
            }).orElseThrow(() -> new NullPointerException("Client not found"));
            return new ResponseEntity<>(newVisit, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

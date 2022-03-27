package com.banshee.core.controller;

import com.banshee.core.controller.exceptions.AttributeNotFoundException;
import com.banshee.core.controller.exceptions.ClientIdNotFoundException;
import com.banshee.core.controller.exceptions.VisitNotFoundException;
import com.banshee.core.entity.Visit;
import com.banshee.core.repository.ClientRepository;
import com.banshee.core.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            }).orElseThrow(() -> new ClientIdNotFoundException(clientId));
            return new ResponseEntity<>(newVisit, HttpStatus.CREATED);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<HttpStatus> deleteVisitById(@PathVariable("id") long id) {
        try {
            visitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable("id") long id, @RequestBody Visit visit) {
        try {
            Visit retrievedVisit = visitRepository.findById(id)
                    .orElseThrow(() -> new VisitNotFoundException(id));
            retrievedVisit.setDate(visit.getDate());
            retrievedVisit.setNet(visit.getNet());
            retrievedVisit.setVisitTotal(visit.getVisitTotal());
            retrievedVisit.setDescription(visit.getDescription());
            return new ResponseEntity<>(visitRepository.save(retrievedVisit), HttpStatus.OK);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

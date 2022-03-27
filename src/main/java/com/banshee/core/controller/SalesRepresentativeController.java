package com.banshee.core.controller;


import com.banshee.core.entity.SalesRepresentative;
import com.banshee.core.repository.SalesRepresentativeRepository;
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
public class SalesRepresentativeController {
    @Autowired
    SalesRepresentativeRepository salesRepresentativeRepository;

    @Autowired
    VisitRepository visitRepository;

    @GetMapping("/salesRepresentative")
    public ResponseEntity<List<SalesRepresentative>> getAllRepresentatives() {
        try {
            List<SalesRepresentative> salesRepresentatives =
                    new ArrayList<SalesRepresentative>(salesRepresentativeRepository.findAll());

            if (salesRepresentatives.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(salesRepresentatives, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/salesRepresentative")
    public ResponseEntity<SalesRepresentative> createRepresentative(
            @RequestBody SalesRepresentative salesRepresentative) {
        try {
            SalesRepresentative savedRepresentative = salesRepresentativeRepository.save(salesRepresentative);
            return new ResponseEntity(savedRepresentative, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/salesRepresentative/{visitId}")
    public ResponseEntity<SalesRepresentative> createLocationIntoClient(
            @PathVariable(value = "visitId") Long visitId,
            @RequestBody SalesRepresentative salesRepresentative) {
        try {
            SalesRepresentative newRepresentative = visitRepository.findById(visitId).map(visit -> {
                long representativeId = salesRepresentative.getId();
                if(representativeId != 0){
                    SalesRepresentative retrievedRepresentative = salesRepresentativeRepository.findById(representativeId)
                            .orElseThrow(() -> new NullPointerException("Representative to add not found"));
                    visit.addSalesRepresentative(retrievedRepresentative);
                    visitRepository.save(visit);
                    return retrievedRepresentative;
                }
                visit.addSalesRepresentative(salesRepresentative);
                return salesRepresentativeRepository.save(salesRepresentative);
            }).orElseThrow(() -> new NullPointerException("Visit not found"));
            return new ResponseEntity<>(newRepresentative, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/salesRepresentative/{id}")
    public ResponseEntity<HttpStatus> deleteRepresentativeById(@PathVariable("id") long id) {
        try {
            salesRepresentativeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/salesRepresentative/{id}")
    public ResponseEntity<SalesRepresentative> updateRepresentative(@PathVariable("id") long id,
                                                           @RequestBody SalesRepresentative salesRepresentative) {
        try {
            SalesRepresentative retrievedRepresentative = salesRepresentativeRepository.findById(id)
                    .orElseThrow(() -> new NullPointerException("Sales Representative not found"));
            retrievedRepresentative.setNit(salesRepresentative.getNit());
            retrievedRepresentative.setName(salesRepresentative.getName());
            return new ResponseEntity<>(salesRepresentativeRepository.save(retrievedRepresentative), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


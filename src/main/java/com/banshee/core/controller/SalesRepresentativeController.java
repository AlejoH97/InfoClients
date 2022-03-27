package com.banshee.core.controller;


import com.banshee.core.entity.Client;
import com.banshee.core.entity.SalesRepresentative;
import com.banshee.core.repository.SalesRepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/salesRepresentative")
    public ResponseEntity<List<SalesRepresentative>> getAllRepresentatives() {
        try {
            List<SalesRepresentative> salesRepresentatives = new ArrayList<SalesRepresentative>(salesRepresentativeRepository.findAll());

            if (salesRepresentatives.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(salesRepresentatives, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/salesRepresentative")
    public ResponseEntity<Client> createRepresentative(@RequestBody SalesRepresentative salesRepresentative) {
        try {
            SalesRepresentative rep = salesRepresentativeRepository.save(salesRepresentative);
            return new ResponseEntity(rep, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


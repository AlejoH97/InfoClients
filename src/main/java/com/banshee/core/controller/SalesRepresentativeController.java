package com.banshee.core.controller;


import com.banshee.core.controller.exceptions.AttributeNotFoundException;
import com.banshee.core.entity.SalesRepresentative;
import com.banshee.core.service.SalesRepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SalesRepresentativeController {
    @Autowired
    SalesRepresentativeService salesRepresentativeService;

    @GetMapping("/salesRepresentative")
    public ResponseEntity<List<SalesRepresentative>> getAllRepresentatives() {
        try {
            List<SalesRepresentative> salesRepresentatives =
                    salesRepresentativeService.getAllRepresentatives();
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
            return new ResponseEntity(
                    salesRepresentativeService.createRepresentative(salesRepresentative),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/salesRepresentative/{visitId}")
    public ResponseEntity<SalesRepresentative> createRepresentativeIntoVisit(
            @PathVariable(value = "visitId") Long visitId,
            @RequestBody SalesRepresentative salesRepresentative) {
        try {
            return new ResponseEntity<>(
                    salesRepresentativeService.createRepresentativeIntoVisit(visitId, salesRepresentative),
                    HttpStatus.CREATED);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/salesRepresentative/{id}")
    public ResponseEntity<HttpStatus> deleteRepresentativeById(@PathVariable("id") long id) {
        try {
            salesRepresentativeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/salesRepresentative/{id}")
    public ResponseEntity<SalesRepresentative> updateRepresentative(@PathVariable("id") long id,
                                                           @RequestBody SalesRepresentative salesRepresentative) {
        try {
            return new ResponseEntity<>(
                    salesRepresentativeService.updateRepresentative(id, salesRepresentative),
                    HttpStatus.OK);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


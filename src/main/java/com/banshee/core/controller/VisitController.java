package com.banshee.core.controller;

import com.banshee.core.controller.exceptions.AttributeNotFoundException;
import com.banshee.core.entity.Visit;
import com.banshee.core.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VisitController extends BaseController {
    @Autowired
    VisitService visitService;

    @GetMapping("/visits")
    public ResponseEntity<List<Visit>> getAllVisits() {
        try {
            List<Visit> visits = visitService.getAllVisits();
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
                                             @Valid @RequestBody Visit visit) {
        try {
            return new ResponseEntity<>(
                    visitService.createVisit(clientId, visit),
                    HttpStatus.CREATED);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<HttpStatus> deleteVisitById(@PathVariable("id") long id) {
        try {
            visitService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable("id") long id, @Valid @RequestBody Visit visit) {
        try {

            return new ResponseEntity<>(
                    visitService.updateVisit(id, visit),
                    HttpStatus.OK);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/visits/{clientID}")
    public ResponseEntity<List<Visit>> getVisitsByClientId(@PathVariable("clientID") long clientID) {
        try {
            List<Visit> visits = visitService.findByClientId(clientID);
            if (visits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(visits, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

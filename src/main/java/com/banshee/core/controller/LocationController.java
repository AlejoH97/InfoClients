package com.banshee.core.controller;

import com.banshee.core.entity.Location;
import com.banshee.core.repository.ClientRepository;
import com.banshee.core.repository.LocationRepository;
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
public class LocationController {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        try {
            List<Location> locations = new ArrayList<>(locationRepository.findAll());

            if (locations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        try {
            Location newLocation = locationRepository.save(location);
            return new ResponseEntity(newLocation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/locations/{clientId}")
    public ResponseEntity<Location> createLocationIntoClient(@PathVariable(value = "clientId") Long clientId,
                                                   @RequestBody Location location) {
        try {
            Location newLocation = clientRepository.findById(clientId).map(client -> {
                long locationId = location.getId();
                if(locationId != 0){
                    Location retrievedLocation = locationRepository.findById(locationId)
                            .orElseThrow(() -> new NullPointerException("Location to add not found"));
                    client.addLocation(retrievedLocation);
                    clientRepository.save(client);
                    return retrievedLocation;
                }
                client.addLocation(location);
                return locationRepository.save(location);
            }).orElseThrow(() -> new NullPointerException("Client not found"));
            return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<HttpStatus> deleteLocationById(@PathVariable("id") long id) {
        try {
            locationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("id") long id, @RequestBody Location location) {
        try {
            Location retrievedLocation = locationRepository.findById(id)
                    .orElseThrow(() -> new NullPointerException("Location not found"));
            retrievedLocation.setCity(location.getCity());
            retrievedLocation.setState(location.getState());
            retrievedLocation.setCountry(location.getCountry());
            return new ResponseEntity<>(locationRepository.save(retrievedLocation), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

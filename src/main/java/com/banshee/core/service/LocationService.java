package com.banshee.core.service;

import com.banshee.core.controller.exceptions.ClientIdNotFoundException;
import com.banshee.core.controller.exceptions.LocationNotFoundException;
import com.banshee.core.entity.Location;
import com.banshee.core.repository.ClientRepository;
import com.banshee.core.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ClientRepository clientRepository;

    public List<Location> getAllLocations() {
        return new ArrayList<>(locationRepository.findAll());
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location createLocationIntoClient(Long clientId, Location location) {
        return clientRepository.findById(clientId).map(client -> {
            long locationId = location.getId();
            if(locationId != 0){
                Location retrievedLocation = locationRepository.findById(locationId)
                        .orElseThrow(() -> new LocationNotFoundException(locationId));
                client.addLocation(retrievedLocation);
                clientRepository.save(client);
                return retrievedLocation;
            }
            client.addLocation(location);
            return locationRepository.save(location);
        }).orElseThrow(() -> new ClientIdNotFoundException(clientId));
    }

    public void deleteById(long id) {
        locationRepository.deleteById(id);
    }

    public Location updateLocation( long id, Location location){
        Location retrievedLocation = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        retrievedLocation.setCity(location.getCity());
        retrievedLocation.setState(location.getState());
        retrievedLocation.setCountry(location.getCountry());
        return locationRepository.save(retrievedLocation);
    }
}

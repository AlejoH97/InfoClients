package com.banshee.core.service;

import com.banshee.core.controller.exceptions.ClientIdNotFoundException;
import com.banshee.core.controller.exceptions.VisitNotFoundException;
import com.banshee.core.entity.Visit;
import com.banshee.core.repository.ClientRepository;
import com.banshee.core.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitService {

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    ClientRepository clientRepository;

    public List<Visit> getAllVisits() {
        return new ArrayList<>(visitRepository.findAll());
    }

    public Visit createVisit(Long clientId, Visit visit) {
        return clientRepository.findById(clientId).map(client -> {
            client.addVisit(visit);
            return visitRepository.save(visit);
        }).orElseThrow(() -> new ClientIdNotFoundException(clientId));
    }

    public void deleteById(long id) {
        visitRepository.deleteById(id);
    }

    public Visit updateVisit(long id, Visit visit) {
        Visit retrievedVisit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException(id));
        retrievedVisit.setDate(visit.getDate());
        retrievedVisit.setNet(visit.getNet());
        retrievedVisit.setVisitTotal(visit.getVisitTotal());
        retrievedVisit.setDescription(visit.getDescription());
        return visitRepository.save(retrievedVisit);
    }
}

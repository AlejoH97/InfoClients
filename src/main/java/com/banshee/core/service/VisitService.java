package com.banshee.core.service;

import com.banshee.core.controller.exceptions.ClientIdNotFoundException;
import com.banshee.core.controller.exceptions.VisitNotFoundException;
import com.banshee.core.entity.Client;
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
            visit.setVisitTotal(calculateVisitTotal(client, visit));
            client.setAvailableCredit(calculateClientCredit(client, visit));
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
        retrievedVisit.setDescription(visit.getDescription());
        retrievedVisit.setVisitTotal(calculateVisitTotal(retrievedVisit.getClient(), visit));
        return visitRepository.save(retrievedVisit);
    }

    private int calculateVisitTotal(Client client, Visit visit){
        return Math.round(visit.getNet() * client.getVisitsPercentage());
    }

    private int calculateClientCredit(Client client, Visit visit){
        return client.getAvailableCredit() - visit.getVisitTotal();
    }
}

package com.banshee.core.service;

import com.banshee.core.controller.exceptions.SalesRepresentativeNotFoundException;
import com.banshee.core.controller.exceptions.VisitNotFoundException;
import com.banshee.core.entity.SalesRepresentative;
import com.banshee.core.repository.SalesRepresentativeRepository;
import com.banshee.core.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesRepresentativeService {
    @Autowired
    SalesRepresentativeRepository salesRepresentativeRepository;

    @Autowired
    VisitRepository visitRepository;

    public List<SalesRepresentative> getAllRepresentatives() {
        return new ArrayList<>(salesRepresentativeRepository.findAll());
    }

    public Object createRepresentative(SalesRepresentative salesRepresentative) {
        return salesRepresentativeRepository.save(salesRepresentative);
    }

    public SalesRepresentative createRepresentativeIntoVisit(Long visitId, SalesRepresentative salesRepresentative) {
        return visitRepository.findById(visitId).map(visit -> {
            long representativeId = salesRepresentative.getId();
            if(representativeId != 0){
                SalesRepresentative retrievedRepresentative = salesRepresentativeRepository.findById(representativeId)
                        .orElseThrow(() -> new SalesRepresentativeNotFoundException(representativeId));
                visit.addSalesRepresentative(retrievedRepresentative);
                visitRepository.save(visit);
                return retrievedRepresentative;
            }
            visit.addSalesRepresentative(salesRepresentative);
            return salesRepresentativeRepository.save(salesRepresentative);
        }).orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public void deleteById(long id) {
        salesRepresentativeRepository.deleteById(id);
    }

    public SalesRepresentative updateRepresentative(long id, SalesRepresentative salesRepresentative) {
        SalesRepresentative retrievedRepresentative = salesRepresentativeRepository.findById(id)
                .orElseThrow(() -> new SalesRepresentativeNotFoundException(id));
        retrievedRepresentative.setNit(salesRepresentative.getNit());
        retrievedRepresentative.setName(salesRepresentative.getName());
        return salesRepresentativeRepository.save(retrievedRepresentative);
    }
}

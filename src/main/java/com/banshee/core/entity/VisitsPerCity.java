package com.banshee.core.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
public class VisitsPerCity {

    @Id
    private String city;
    private int visits;

    public VisitsPerCity() {
    }

    public VisitsPerCity(String city, int visits) {
        super();
        this.city = city;
        this.visits = visits;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
}

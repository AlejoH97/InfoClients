package com.banshee.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
public class Visit {
    @Id
    @GeneratedValue
    private long id;
    @PastOrPresent
    private Date date;
    @Min(0)
    private int net;
    private int visitTotal;
    private String description;

    @ManyToMany
    @JoinTable(name = "visit_representative",
            joinColumns = { @JoinColumn(name = "visit.id") },
            inverseJoinColumns = { @JoinColumn(name = "representative.id") })
    private Set<SalesRepresentative> salesRepresentatives = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="client.id")
    @JsonIgnore
    private Client client;

    public Visit(){}

    public Visit(Date date, Set<SalesRepresentative> salesRepresentatives, int net, int visitTotal, String description, Client client) {
        super();
        this.date = date;
        this.salesRepresentatives = salesRepresentatives;
        this.net = net;
        this.visitTotal = visitTotal;
        this.description = description;
        this.client = client;
    }

    public void addSalesRepresentative(SalesRepresentative salesRepresentative){
        this.salesRepresentatives.add(salesRepresentative);
        salesRepresentative.getVisits().add(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<SalesRepresentative> getSalesRepresentatives() {
        return salesRepresentatives;
    }

    public void setSalesRepresentatives(Set<SalesRepresentative> salesRepresentatives) {
        this.salesRepresentatives = salesRepresentatives;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getVisitTotal() {
        return visitTotal;
    }

    public void setVisitTotal(int visitTotal) {
        this.visitTotal = visitTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}

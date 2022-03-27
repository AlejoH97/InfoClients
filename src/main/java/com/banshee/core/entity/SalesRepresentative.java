package com.banshee.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
@Table(name = "representative")
public class SalesRepresentative {

    @Id
    @GeneratedValue
    private long id;
    private String nit;
    private String name;

    @ManyToMany(mappedBy = "salesRepresentatives")
    @JsonIgnore
    private Set<Visit> visits = new HashSet<>();

    public SalesRepresentative(){}

    public SalesRepresentative(String nit, String name) {
        super();
        this.nit = nit;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getName() {
        return name;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
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

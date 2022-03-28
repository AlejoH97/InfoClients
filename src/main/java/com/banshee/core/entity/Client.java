package com.banshee.core.entity;

import com.banshee.core.service.AttributeEncryptor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
public class Client {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    @Convert(converter = AttributeEncryptor.class)
    private String nit;
    @Column(nullable = false)
    private String fullName;
    private String address;
    private String phone;
    private int creditLimit;
    private int availableCredit;
    private float visitsPercentage;

    @ManyToMany
    @JoinTable(name = "client_location",
            joinColumns = { @JoinColumn(name = "client.id") },
            inverseJoinColumns = { @JoinColumn(name = "location.id") })
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

    public Client() {
    }

    public Client(long id, String nit, String fullName, String address, String phone, Set<Location> location, int creditLimit, int availableCredit, int visitsPercentage, Set<Visit> visits) {
        super();
        this.id = id;
        this.nit = nit;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.locations = location;
        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
        this.visitsPercentage = visitsPercentage;
        this.visits = visits;
    }

    public void addLocation(Location location){
        this.locations.add(location);
        location.getClients().add(this);
    }

    public void addVisit(Visit visit){
        this.visits.add(visit);
        visit.setClient(this);
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(int availableCredit) {
        this.availableCredit = availableCredit;
    }

    public float getVisitsPercentage() {
        return visitsPercentage;
    }

    public void setVisitsPercentage(float visitsPercentage) {
        this.visitsPercentage = visitsPercentage;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
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

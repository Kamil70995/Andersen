package ru.andersen.travelagency.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
@javax.persistence.Entity
@Table(name = "tours")
public class Tour extends Entity {
    @Id
    @GeneratedValue
    private long tour_id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "summary", nullable = false)
    private String summary;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "departureDate", nullable = false)
    private String departureDate;
    @Column(name = "arrivalDate", nullable = false)
    private String arrivalDate;
    @Column(name = "price", nullable = false)
    private double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", cascade = CascadeType.ALL)
    private ArrayList<Country> countries;


    public Tour(long tour_id, String name, String summary, String description, String departureDate, String arrivalDate, double price) {
        super();
        this.tour_id = tour_id;
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
    }

    public Tour(String name, String summary, String description, String departureDate, String arrivalDate, double price) {
        super();
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
    }

    public long getTour_id() {
        return tour_id;
    }

    public void setTour_id(long tour_id) {
        this.tour_id = tour_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

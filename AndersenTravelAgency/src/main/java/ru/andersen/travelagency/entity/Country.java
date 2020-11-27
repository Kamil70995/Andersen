package ru.andersen.travelagency.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@javax.persistence.Entity
@Table(name = "countries")
public class Country extends Entity {
    @Id
    @GeneratedValue
    private long country_id;
    @Column(name = "nameCountry", nullable = false)
    private String nameCountry;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country", cascade = CascadeType.ALL)
    private List<Hotel> hotels = new ArrayList<>();

    public Country(long country_id, String nameCountry) {
        this.country_id = country_id;
        this.nameCountry = nameCountry;
    }

    public Country(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public long getCountry_id() {
        return country_id;
    }

    public void setCountry_id(long country_id) {
        this.country_id = country_id;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}

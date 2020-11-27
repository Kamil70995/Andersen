package ru.andersen.travelagency.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "hotels")
public class Hotel extends Entity {
    @Id
    @GeneratedValue
    int hotel_id;
    @Column(name = "hotelName", nullable = false)
    private String hotelName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Hotel(int hotel_id, String hotelName) {
        this.hotel_id = hotel_id;
        this.hotelName = hotelName;
    }

    public Hotel(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

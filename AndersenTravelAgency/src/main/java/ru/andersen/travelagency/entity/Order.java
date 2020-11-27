package ru.andersen.travelagency.entity;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "orders")
public class Order extends Entity {
    @Id
    @GeneratedValue
    private Long order_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    @OneToOne
    @JoinColumn(name = "tour_id")
    private Tour tour_id;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "totalPrice", nullable = false)
    private double totalPrice;
    @Column(name = "orderDate", nullable = false)
    private Date orderDate;
    @Column(name = "departureDate", nullable = false)
    private Date departureDate;
    @Column(name = "arrivalDate", nullable = false)
    private Date arrivalDate;

    public Order(Long order_id, User user_id, Tour tour_id, int quantity, double totalPrice, Date orderDate, Date departureDate, Date arrivalDate) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public Order(User user_id, Tour tour_id, int quantity, double totalPrice, Date orderDate, Date departureDate, Date arrivalDate) {
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public Long getOrderId() {
        return order_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Tour getTour_id() {
        return tour_id;
    }

    public void setTour_id(Tour tour_id) {
        this.tour_id = tour_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}

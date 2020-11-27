package ru.andersen.travelagency.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "reviews")
public class Review extends Entity {
    @Id
    @GeneratedValue
    private long review_id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "like", nullable = false)
    private boolean like;

    public Review(long review_id, String description, boolean like) {
        this.review_id = review_id;
        this.description = description;
        this.like = like;
    }

    public Review(String description, boolean like) {
        this.description = description;
        this.like = like;
    }

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}

package com.example.WithPet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class BusinessUser {
    @Id
    private Long businessId;
    @JoinColumn(name="user_id")
    private String userId;

    public Long getUserId() {
        return businessId;
    }

    public void setUserId(Long businessId) {
        this.businessId = businessId;
    }

    public String getUser() {
        return userId;
    }

    public void setUser(String userid) {
        this.userId = userid;
    }
}

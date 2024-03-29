package com.project.withpet.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ordertable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    @JoinColumn(name = "user_id")
    private String userid;
    @Column
    private LocalDateTime orderdate;

    public Ordertable() {

    }

    public Long getOrderid() {
        return orderid;
    }

    public Ordertable( String userid, LocalDateTime orderdate) {
        this.userid = userid;
        this.orderdate = orderdate;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String id) {
        this.userid = id;
    }

    public LocalDateTime getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDateTime orderdate) {
        this.orderdate = orderdate;
    }

    @PrePersist
    public  void orderdate() {
        this.orderdate = LocalDateTime.now();
    }
}

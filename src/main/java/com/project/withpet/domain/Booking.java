package com.project.withpet.domain;

import javax.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;
    private String checkin;
    private String checkout;
    private Long total;

    @JoinColumn(name = "user_userid")
    private String userid;
    private String detail;

    @JoinColumn(name = "hotelroom_roomid")
    private Long roomid;

    private String name;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

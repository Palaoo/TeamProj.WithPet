package com.project.withpet.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class Hotelroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomid;

    @JoinColumn(name = "shop_shopid")
    private Long shopid;

    @Column
    private String roomname;

    @Column
    private Long price;

    @Column
    private Long person;

    @Column
    private String content;


    @OneToMany(mappedBy = "shopid", cascade = CascadeType.REMOVE)
    private List<Hotelroomimg> hotelroomimgList;

    @OneToMany(mappedBy = "roomid", cascade = CascadeType.REMOVE)
    private List<Booking> bookings;

    public Hotelroom(Long shopid, String roomname, Long price, Long person, String content) {
        this.shopid = shopid;
        this.roomname = roomname;
        this.price = price;
        this.person = person;
        this.content = content;
    }

    public Hotelroom(Long roomid, Long shopid, String roomname, Long price, Long person, String content) {
        this.roomid = roomid;
        this.shopid = shopid;
        this.roomname = roomname;
        this.price = price;
        this.person = person;
        this.content = content;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Hotelroomimg> getHotelroomimgList() {
        return hotelroomimgList;
    }

    public void setHotelroomimgList(List<Hotelroomimg> hotelroomimgList) {
        this.hotelroomimgList = hotelroomimgList;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

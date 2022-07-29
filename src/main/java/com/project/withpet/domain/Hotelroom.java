package com.project.withpet.domain;

import javax.persistence.*;

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

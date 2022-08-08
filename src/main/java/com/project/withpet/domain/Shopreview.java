package com.project.withpet.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "shopreview")
public class Shopreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;


    @JoinColumn(name = "shop_shopid")
    private Long shopid;

    @Column
    private String content;

    @Column
    @CreatedDate
    private String date;

    @Column
    private Long score;

    @JoinColumn(name = "User_userid")
    private String userid;

//    @ManyToOne
//    @JoinColumn(name = "userid")
//    private member member;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

//    @ManyToMany
//    @JoinColumn(name = "userid")
//    private member member;
}

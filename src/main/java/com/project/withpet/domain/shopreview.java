package com.project.withpet.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "shopreview")
public class shopreview {

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

//    @ManyToMany
//    @JoinColumn(name = "userid")
//    private member member;
}
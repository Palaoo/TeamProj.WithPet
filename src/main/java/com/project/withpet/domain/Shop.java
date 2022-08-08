package com.project.withpet.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopid;
    @Column
    private Long bid;
    @Column
    private String name;
    @Column
    private String intro;
    @Column
    private String hour;
    @Column
    private String info;
    @Column
    private String address;
    @Column
    private String tel;

    @ManyToOne
    @JoinColumn(name = "regid")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "typeid")
    private Shoptype shoptype;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Featlist",
            joinColumns = @JoinColumn(name = "shopid"),
            inverseJoinColumns = @JoinColumn(name = "featid")
    )
    List<Feat> shopFeats;


    @OneToMany(mappedBy = "shopid", cascade = CascadeType.REMOVE)
    private List<shoplike> shoplikes;

    @OneToMany(mappedBy = "shopId", cascade = CascadeType.REMOVE)
    private List<LikeHotel> likeHotels;

    @OneToMany(mappedBy = "shopid", cascade = CascadeType.REMOVE)
    private List<Hotelimg> hotelimgs;

    @OneToMany(mappedBy = "shopid", cascade = CascadeType.REMOVE)
    private List<Shopreview> shopreviews;

    @OneToMany(mappedBy = "shopid", cascade = CascadeType.REMOVE)
    private List<Hotelroom> hotelrooms;



}

package com.project.withpet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hotelimg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "shop_shopid")
    private Long shopid;
    @Column
    private String name;
    @Column
    private String origname;
    @Column
    private String path;

    public Hotelimg(Long shopid, String name, String origname, String path) {
        this.shopid = shopid;
        this.name = name;
        this.origname = origname;
        this.path = path;
    }
}

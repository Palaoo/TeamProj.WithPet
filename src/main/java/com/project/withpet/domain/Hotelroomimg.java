package com.project.withpet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hotelroomimg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD
=======

>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
    @JoinColumn(name = "hotelroom_roomid")
    private Long shopid;
    @Column
    private String name;
    @Column
    private String origname;
    @Column
    private String path;
<<<<<<< HEAD
=======

    public Hotelroomimg(Long shopid, String name, String origname, String path) {
        this.shopid = shopid;
        this.name = name;
        this.origname = origname;
        this.path = path;
    }
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}

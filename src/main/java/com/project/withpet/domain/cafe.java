package com.project.withpet.domain;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity
@Table(name="shop")
public class cafe {

    @Id
    @Column(name = "shopid")
    private Long shopid;

    @Column(name = "name")
    private String name;

    @Column(name = "intro")
    private String intro;

    @Column(name = "hour")
    private String hour;

    @Column(name = "info")
    private String info;

    @Column(name = "address")
    private String address;

    @Column(name = "tel")
    private String tel;

    @ManyToOne
    @JoinColumn (name = "regid")
    private Region region;

    @ManyToOne
    @JoinColumn (name = "typeid")
    private Shoptype shoptype;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "featlist",
            joinColumns = @JoinColumn(name = "shopid"),
            inverseJoinColumns = @JoinColumn(name = "featid")
    )
    List<Feat> shopFeats;



//    @ManyToOne
//    @JoinColumn (name = "shopid")
//    private featlist featlist;

//    @ManyToMany
//    @JoinTable(name = "featlist",
//                joinColumns = @JoinColumn(name = "shopid"),
//                inverseJoinColumns = @JoinColumn(name = "featid"))
//    private List<feat> fearList = new ArrayList<>();
//    @OneToMany(mappedBy = "cafe")
//    private List<featlist> featlist;

//    public cafe(Long shopid, String name, Long regid, Long typeid) {
//        this.shopid = shopid;
//        this.name = name;
//        this.regid = regid;
//        this.typeid = typeid;
//    }

}

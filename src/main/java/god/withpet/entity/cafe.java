package god.withpet.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
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
    private region region;

    @ManyToOne
    @JoinColumn (name = "typeid")
    private shoptype shoptype;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "featlist",
            joinColumns = @JoinColumn(name = "shopid"),
            inverseJoinColumns = @JoinColumn(name = "featid")
    )
    List<feat> shopFeats;



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

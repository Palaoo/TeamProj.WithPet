package com.project.withpet.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Feat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featid;
    @Column
    private String featname;

//    @ManyToMany(mappedBy = "shopFeats")
//    List<Shop> feats;

    public Long getFeatid() {
        return featid;
    }

    public void setFeatid(Long featid) {
        this.featid = featid;
    }

    public String getFeatname() {
        return featname;
    }

    public void setFeatname(String featname) {
        this.featname = featname;
    }
}

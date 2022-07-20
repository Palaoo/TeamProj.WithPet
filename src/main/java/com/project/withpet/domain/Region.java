package com.project.withpet.domain;

import javax.persistence.*;

@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regid;

    @Column
    private String regname;

    public Long getRegid() {
        return regid;
    }

    public void setRegid(Long regid) {
        this.regid = regid;
    }

    public String getRegname() {
        return regname;
    }

    public void setRegname(String regname) {
        this.regname = regname;
    }
}

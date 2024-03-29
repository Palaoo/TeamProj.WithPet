package com.project.withpet.domain;

import javax.persistence.*;

@Entity
public class Boardimg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_boardcode")
    private Long boardcode;

    @Column
    private String name;
    @Column
    private String origname;
    @Column
    private String path;

    public Boardimg() {
    }

    public Boardimg(Long boardcode, String name, String origname, String path) {
        this.boardcode = boardcode;
        this.name = name;
        this.origname = origname;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardcode() {
        return boardcode;
    }

    public void setBoardcode(Long boardcode) {
        this.boardcode = boardcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigname() {
        return origname;
    }

    public void setOrigname(String origname) {
        this.origname = origname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

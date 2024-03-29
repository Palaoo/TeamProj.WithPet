package com.project.withpet.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardcode;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    private String date;

    @JoinColumn(name = "user_userid")
    private String writer;
    private String title;

    @PrePersist
    public void date(){

        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @OneToMany(mappedBy = "boardcode", cascade = CascadeType.REMOVE)
    private List<Reply> replys;

    @OneToMany(mappedBy = "boardcode", cascade = CascadeType.REMOVE)
    private List<Boardimg> boardimgs;

    public List<Reply> getReplys() {
        return replys;
    }

    public void setReplys(List<Reply> replys) {
        this.replys = replys;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getBoardcode() {
        return boardcode;
    }

    public void setBoardcode(Long boardcode) {
        this.boardcode = boardcode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

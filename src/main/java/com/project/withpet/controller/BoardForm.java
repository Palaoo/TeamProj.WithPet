package com.project.withpet.controller;

public class BoardForm {
    private Long boardCode;
    private String content;
    private String date;
    private String writer;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getBoardCode() {
        return boardCode;
    }

    public void setBoardCode(Long boardCode) {
        this.boardCode = boardCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}

package com.project.withpet.controller;

import com.project.withpet.domain.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public cafe getCafe() {
        return cafe;
    }

    public void setCafe(cafe cafe) {
        this.cafe = cafe;
    }

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public CafeDTOList() {
    }

    public CafeDTOList(cafe cafe, boolean islike, String path) {
        this.cafe = cafe;
        this.islike = islike;
        this.path = path;
    }
}

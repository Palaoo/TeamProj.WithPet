package com.project.withpet.dto;

import com.project.withpet.domain.cafe;

import java.util.Optional;

public class CafeDTOList {
    cafe cafe;
    boolean islike;
    String path;
    double scoreAvg;
    private Long likeCount;
    Long reviewCount;

    public CafeDTOList(cafe cafe, boolean shopLike, String path, Long likeCount) {
    }

    public CafeDTOList(cafe cafe, boolean islike, String path, double scoreAvg, Long likeCount) {
        this.cafe = cafe;
        this.islike = islike;
        this.path = path;
        this.scoreAvg = scoreAvg;
        this.likeCount = likeCount;
    }

    public CafeDTOList(Optional<com.project.withpet.domain.cafe> cafe, boolean shopLike, Long likeCount) {
    }

    public CafeDTOList(Optional<com.project.withpet.domain.cafe> cafe, boolean shopLike, String path, Long likeCount) {
    }

    public Long getReviewCount() {
        return reviewCount;
    }
    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }
    public double getScoreAvg() {
        return scoreAvg;
    }
    public void setScoreAvg(double scoreAvg) {
        this.scoreAvg = scoreAvg;
    }
    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

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


    public CafeDTOList(com.project.withpet.domain.cafe cafe, boolean islike, String path, Long likeCount, double scoreAvg) {
        this.cafe = cafe;
        this.islike = islike;
        this.path = path;
        this.likeCount = likeCount;
        this.scoreAvg = scoreAvg;
    }
}

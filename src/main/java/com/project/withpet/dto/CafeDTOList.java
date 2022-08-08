package com.project.withpet.dto;


import com.project.withpet.domain.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;

<<<<<<< HEAD
=======
    private Long likeCount;

    public CafeDTOList(com.project.withpet.domain.cafe cafe, boolean islike, Long likeCount) {
        this.cafe = cafe;
        this.islike = islike;
        this.likeCount = likeCount;
    }

>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
    public com.project.withpet.domain.cafe getCafe() {
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
<<<<<<< HEAD

    public CafeDTOList(cafe cafe, boolean islike) {
        this.cafe = cafe;
        this.islike = islike;
    }
=======
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}

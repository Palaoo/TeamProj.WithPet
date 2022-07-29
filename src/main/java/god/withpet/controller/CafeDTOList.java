package god.withpet.controller;

import god.withpet.entity.cafe;

public class CafeDTOList {
    cafe cafe;
    boolean islike;

    public god.withpet.entity.cafe getCafe() {
        return cafe;
    }

    public void setCafe(god.withpet.entity.cafe cafe) {
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

    public CafeDTOList(god.withpet.entity.cafe cafe, boolean islike) {
        this.cafe = cafe;
        this.islike = islike;
    }
}

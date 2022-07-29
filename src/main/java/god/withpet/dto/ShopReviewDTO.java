package god.withpet.dto;

import god.withpet.entity.cafe;
import god.withpet.entity.shopreview;

public class ShopReviewDTO {
    shopreview shopreview;
    cafe cafe;

    public god.withpet.entity.shopreview getShopreview() {
        return shopreview;
    }

    public void setShopreview(god.withpet.entity.shopreview shopreview) {
        this.shopreview = shopreview;
    }

    public god.withpet.entity.cafe getCafe() {
        return cafe;
    }

    public void setCafe(god.withpet.entity.cafe cafe) {
        this.cafe = cafe;
    }

    public ShopReviewDTO() {
    }

    public ShopReviewDTO(god.withpet.entity.shopreview shopreview, god.withpet.entity.cafe cafe) {
        this.shopreview = shopreview;
        this.cafe = cafe;
    }
}

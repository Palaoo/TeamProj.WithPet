package com.project.withpet.dto;

import com.project.withpet.domain.shopreview;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class reviewDto {

    private Long rid;
    private String content;
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private Long score;
    private String userid;
    private Long shopid;



    //    public reviewDto(shopreview shopreview) {
//        this.rid = shopreview.getRid();
//        this.content = shopreview.getContent();
//        this.date = shopreview.getDate();
//        this.score = shopreview.getScore();
//        this.userid = shopreview.getMember().getUserid();
//        this.shopid = shopreview.getCafe().getShopid();
//    }
    public shopreview toEntity() {

        return new shopreview(rid, shopid, content, date, score, userid);
    }

}

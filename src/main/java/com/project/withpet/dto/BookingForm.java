package com.project.withpet.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class BookingForm {
    private Long bookid;
    private String checkin;
    private String checkout;
    private Long total;
    private String detail;
    private String roomname;
    private String hotelname;
    private String name;
    private Long shopid;
    private String mobile;
}

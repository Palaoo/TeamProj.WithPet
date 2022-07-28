package com.project.withpet.controller;

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
    private String chekout;
    private Long total;
    private String detail;
    private String roomname;
    private String hotelname;
    private String name;
}

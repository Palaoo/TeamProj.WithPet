package com.project.withpet.dto;

import com.project.withpet.domain.Booking;
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
    private String path;
    private Booking booking;

    public BookingForm(String roomname, String hotelname, String path, Booking booking) {
        this.roomname = roomname;
        this.hotelname = hotelname;
        this.path = path;
        this.booking = booking;
    }
}

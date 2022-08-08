package com.project.withpet.dto;

<<<<<<< HEAD
=======
import com.project.withpet.domain.Booking;
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
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
<<<<<<< HEAD
=======
    private String path;
    private Booking booking;

    public BookingForm(String roomname, String hotelname, String path, Booking booking) {
        this.roomname = roomname;
        this.hotelname = hotelname;
        this.path = path;
        this.booking = booking;
    }
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}

package com.project.withpet.controller;

import com.project.withpet.controller.form.BookingForm;
import com.project.withpet.domain.Booking;
import com.project.withpet.domain.Hotelroom;
import com.project.withpet.domain.Shop;
import com.project.withpet.repository.BookingRepository;
import com.project.withpet.service.HotelroomService;
import com.project.withpet.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class UserInfoController {

    private final BookingRepository bookingRepository;
    private final HotelroomService hotelroomService;
    private final ShopService shopService;

    @Autowired
    public UserInfoController(BookingRepository bookingRepository, HotelroomService hotelroomService, ShopService shopService) {
        this.bookingRepository = bookingRepository;
        this.hotelroomService = hotelroomService;
        this.shopService = shopService;
    }

    @GetMapping("/mypage/booking")
    public String userInfo(HttpServletRequest req, Model model){

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            String userid = (String) session.getAttribute("userid");
            model.addAttribute("userid", userid);
            List<Booking> booking = bookingRepository.findAllByUserid(userid);
            List<BookingForm> bookingList = new ArrayList<>();
            for(int i = 0; i < booking.size(); i++){
                BookingForm bookingForm = new BookingForm();
                bookingForm.setBookid(booking.get(i).getBookid());
                bookingForm.setCheckin(booking.get(i).getCheckin());
                bookingForm.setCheckout(booking.get(i).getCheckout());
                bookingForm.setTotal(booking.get(i).getTotal());
                bookingForm.setDetail(booking.get(i).getDetail());
                bookingForm.setName(booking.get(i).getName());
                bookingForm.setMobile(booking.get(i).getMobile());
                Optional<Hotelroom> hotelroomOptional = hotelroomService.findById(booking.get(i).getRoomid());
                Long shopid = hotelroomOptional.get().getShopid();
                bookingForm.setShopid(shopid);
                Optional<Shop> shopOptional = shopService.findById(shopid);
                bookingForm.setRoomname(hotelroomOptional.get().getRoomname());
                bookingForm.setHotelname(shopOptional.get().getName());
                bookingList.add(bookingForm);
            }

            model.addAttribute("bookList", bookingList);
            return "mypage/mypage_booking";
        }

    }

    @GetMapping("mypage/booking/change")
    public String changeBook(HttpServletRequest req, Model model,
                             @RequestParam("bookid") Long bookid) throws ParseException {

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userid", session.getAttribute("userid"));
            Optional<Booking> bookingOptional = bookingRepository.findById(bookid);
            model.addAttribute("booking", bookingOptional.get());

            String checkin = bookingOptional.get().getCheckin();
            String checkout = bookingOptional.get().getCheckout();
            Date checkinDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkin);
            Date checkoutDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkout);
            Long diffSec = (checkoutDate.getTime() - checkinDate.getTime()) / 1000;
            Long diffDays = diffSec / (24*60*60);
            model.addAttribute("days", diffDays);
            return "mypage/mypage_booking_change";
        }
    }

    @PostMapping("/mypage/booking/change")
    public String changeBooking(HttpServletRequest req, Model model,
                                @RequestParam("bookid") Long bookid,
                                @RequestParam("name") String name,
                                @RequestParam("detail") String detail,
                                @RequestParam("mobile") String mobile){
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userid", session.getAttribute("userid"));

            Optional<Booking> bookingOptional = bookingRepository.findById(bookid);
            Booking booking = bookingOptional.get();
            booking.setBookid(bookid);
            booking.setName(name);
            booking.setDetail(detail);
            booking.setMobile(mobile);
            bookingRepository.save(booking);

            return "redirect:/mypage/booking";
        }
    }

    @GetMapping("mypage/booking/delete")
    public String deleteBooking(@RequestParam("bookid") Long bookid){
        bookingRepository.deleteById(bookid);
        return "redirect:/mypage/booking";
    }

    @GetMapping("/mypage/manager")
    public String register(HttpServletRequest req, Model model){

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userid", session.getAttribute("userid"));
            return "mypage/mypage_manager";
        }
    }

}

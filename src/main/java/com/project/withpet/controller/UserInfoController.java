package com.project.withpet.controller;

import com.project.withpet.domain.Booking;
import com.project.withpet.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class UserInfoController {

    private final BookingRepository bookingRepository;

    @Autowired
    public UserInfoController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
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
            for(int i = 0; i < booking.size(); i++){
//                booking.get(i).g
            }

            model.addAttribute("bookList", booking);
            return "mypage/booking";
        }

    }

    @GetMapping("/mypage/manager")
    public String register(HttpServletRequest req, Model model){

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userid", session.getAttribute("userid"));
            return "mypage/manager";
        }
    }

}

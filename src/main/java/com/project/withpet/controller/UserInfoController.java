package com.project.withpet.controller;

import com.project.withpet.domain.*;
import com.project.withpet.dto.BoardForm;
import com.project.withpet.dto.BookingForm;
import com.project.withpet.repository.Booking.BookingRepository;

import com.project.withpet.repository.Booking.HotelroomQueryRepository;
import com.project.withpet.repository.HotelimgRepository;
import com.project.withpet.repository.HotelroomimgRepository;
import com.project.withpet.service.*;
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
    private final BoardService boardService;
    private final BoardimgService boardimgService;

    private final HotelimgRepository hotelimgRepository;
    private final BusinessUserService businessUserService;
    private final HotelroomQueryRepository hotelroomQueryRepository;
    private final HotelroomimgRepository hotelroomimgRepository;

    @Autowired
    public UserInfoController(BookingRepository bookingRepository, HotelroomService hotelroomService, ShopService shopService, BoardService boardService, HotelimgRepository hotelimgRepository, BoardimgService boardimgService, HotelimgRepository hotelimgRepository1, BusinessUserService businessUserService, HotelroomQueryRepository hotelroomQueryRepository, HotelroomimgRepository hotelroomimgRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelroomService = hotelroomService;
        this.shopService = shopService;
        this.boardService = boardService;
        this.boardimgService = boardimgService;

        this.hotelimgRepository = hotelimgRepository1;
        this.businessUserService = businessUserService;
        this.hotelroomQueryRepository = hotelroomQueryRepository;
        this.hotelroomimgRepository = hotelroomimgRepository;
    }

    @GetMapping("/mypage/booking")
    public String userInfo(HttpServletRequest req, Model model) {

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {

            if (businessUserService.isBusinessUser(req.getSession().getAttribute("userid").toString()) != -1L) {
                model.addAttribute("businessId", businessUserService.findByUid(req.getSession().getAttribute("userid").toString()).getBid());
                req.getSession().setAttribute("businessId", model.getAttribute("businessId").toString());
                Long bid = businessUserService.findByUid(req.getSession().getAttribute("userid").toString()).getBid();
                List<BookingForm> bookingForms = new ArrayList<>();
                if (hotelroomQueryRepository.findBookingByBid(bid) != null) {
                    List<Booking> bookingByBid = new ArrayList<>();
                    bookingByBid = hotelroomQueryRepository.findBookingByBid(bid);
                    for (Booking booking : bookingByBid) {
                        String path = hotelroomimgRepository.findByShopid(booking.getRoomid()).get().getPath();
                        String hotelname = shopService.findById(hotelroomService.findById(booking.getRoomid()).get().getShopid()).get().getName();
                        String roomname = hotelroomService.findById(booking.getRoomid()).get().getRoomname();
                        bookingForms.add(new BookingForm(roomname, hotelname, path, booking));
                    }
                }
                model.addAttribute("bidBookList", bookingForms);
            }
            String userid = (String) session.getAttribute("userid");
            model.addAttribute("userid", userid);
            List<Booking> booking = bookingRepository.findAllByUserid(userid);
            List<BookingForm> bookingList = new ArrayList<>();

            for (int i = 0; i < booking.size(); i++) {
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

                Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopOptional.get().getShopid());
                bookingForm.setPath(hotelimg.get().getPath());
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

            Long diffDays = diffSec / (24 * 60 * 60);
            model.addAttribute("days", diffDays);
            return "mypage/mypage_booking_change";
        }
    }

    @PostMapping("/mypage/booking/change")
    public String changeBooking(HttpServletRequest req, Model model,
                                @RequestParam("bookid") Long bookid,
                                @RequestParam("name") String name,
                                @RequestParam("detail") String detail,
                                @RequestParam("mobile") String mobile) {
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
    public String deleteBooking(@RequestParam("bookid") Long bookid) {
        bookingRepository.deleteById(bookid);
        return "redirect:/mypage/booking";
    }

//    @GetMapping("/mypage/manager")
//    public String register(HttpServletRequest req, Model model){
//
//        HttpSession session = req.getSession();
//        if (session.getAttribute("userid") == null) {
//            return "redirect:/login";
//        } else {
//            model.addAttribute("userid", session.getAttribute("userid"));
//            return "mypage/mypage_manager";
//        }
//    }

    @GetMapping("/mypage/post")

    public String mypost(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/login";
        } else {
            String userid = (String) session.getAttribute("userid");
            model.addAttribute("userid", userid);
            List<Board> boardList = boardService.findByUserid(userid);
            List<BoardForm> boardFormList = new ArrayList<>();

            for (Board board : boardList) {
                Optional<Boardimg> boardimg = boardimgService.findOne(board.getBoardcode());
                String path = "";
                if (boardimg.isPresent()) {
                    path = boardimg.get().getPath();
                } else {
                    path = "/image/catbg.jpg";
                }
                boardFormList.add(new BoardForm(path, board));
            }
            model.addAttribute("postList", boardFormList);
            return "mypage/mypage_post";
        }
    }


}

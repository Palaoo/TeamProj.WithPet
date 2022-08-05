package com.project.withpet.controller;

import com.project.withpet.domain.Hotelimg;
import com.project.withpet.domain.LikeHotel;
import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shoplike;
import com.project.withpet.dto.LikeshopDTO;
import com.project.withpet.repository.HotelimgRepository;
import com.project.withpet.repository.LikeHotel.SpringLikeHotelRepository;
import com.project.withpet.repository.ShopLike.LikeShopRepository;
import com.project.withpet.service.ShopLikeService;
import com.project.withpet.service.cafeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ShopLikeController {
    private final ShopLikeService shopLikeService;
    private final cafeService cafeService;

    private final LikeShopRepository likeshopRepository;

    private final HotelimgRepository hotelimgRepository;
    private final SpringLikeHotelRepository springLikeHotelRepository;

    private final Tools tools = new Tools();

    public ShopLikeController(ShopLikeService shopLikeService, com.project.withpet.service.cafeService cafeService, LikeShopRepository likeshopRepository, HotelimgRepository hotelimgRepository, SpringLikeHotelRepository springLikeHotelRepository) {
        this.shopLikeService = shopLikeService;
        this.cafeService = cafeService;
        this.likeshopRepository = likeshopRepository;
        this.hotelimgRepository = hotelimgRepository;
        this.springLikeHotelRepository = springLikeHotelRepository;
    }

    @GetMapping("/mypage/myshop")  //마이페이지 좋아요 리스트
    public String showMyshop(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userLogined", userid);

        if (!tools.isUserLogined(req)) {
            return "/login";
        }

        List<shoplike> shoplikeList = shopLikeService.findByUserId(userid);
        List<LikeshopDTO> likeshopDTOList = new ArrayList<>();
        for (int i = 0; i < shoplikeList.size(); i++) {
            shoplike shoplike = shoplikeList.get(i);
            Optional<cafe> cafe = cafeService.findById(shoplike.getShopid());
            Optional<Hotelimg> shopimg = hotelimgRepository.findByShopid(shoplike.getShopid());
            String path = shopimg.get().getPath();
            likeshopDTOList.add(new LikeshopDTO(shoplike, cafe.get(), path));
        }

        model.addAttribute("likeshopDTOList", likeshopDTOList);


        List<LikeHotel> hotellikeList = springLikeHotelRepository.findByUserId(userid);
        List<LikeshopDTO> likehotelDTOList = new ArrayList<>();
        for (int i = 0; i < shoplikeList.size(); i++) {
            LikeHotel likeHotel = hotellikeList.get(i);
            Optional<cafe> hotel = cafeService.findById(likeHotel.getShopId());
            Optional<Hotelimg> shopimg = hotelimgRepository.findByShopid(likeHotel.getShopId());
            String path = shopimg.get().getPath();
            likehotelDTOList.add(new LikeshopDTO(likeHotel, hotel.get(), path));
        }


        model.addAttribute("likehotelDTOList", likehotelDTOList);

        return "myshop";
    }

    @GetMapping("/mypage/myshop/delete") //좋아요 삭제
    public String deleteMyShop(LikeshopDTO dto, @RequestParam("likeid") Long likeid) {
        likeshopRepository.deleteById(likeid);
        return "redirect:/mypage/myshop";
    }
    @GetMapping("/mypage/myshop/deletehotel") //좋아요 삭제
    public String deleteMyHotel(LikeshopDTO dto, @RequestParam("hotellikeid") Long hotellikeid) {
        springLikeHotelRepository.deleteById(hotellikeid);
        return "redirect:/mypage/myshop";
    }


    @GetMapping("append_shoplike")
    @ResponseBody
    public String appendShopLike(HttpServletRequest req, @RequestParam Long shopId) {
        System.out.println("ShopLikeController appendShopLike(), , shopId");
        if (shopLikeService.saveordelete(req.getSession().getAttribute("userLogined").toString(),
                shopId) == 0) {
            return "0";
        }
        return "1";
    }


}

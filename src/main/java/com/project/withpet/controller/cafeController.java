package com.project.withpet.controller;

import com.project.withpet.domain.Hotelimg;
import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shopreview;
import com.project.withpet.repository.HotelimgRepository;
import com.project.withpet.repository.cafeRepository;
import com.project.withpet.repository.shopreviewRepository;
import com.project.withpet.service.ShopLikeService;
import com.project.withpet.service.cafeService;
import com.project.withpet.service.reviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class cafeController {

    @Autowired
    private cafeService cafeService;

    @Autowired
    private cafeRepository cafeRepository;

    @Autowired
    private reviewService reviewService;

    @Autowired
    private shopreviewRepository shopreviewRepository;

    private final ShopLikeService shopLikeService;
    private final HotelimgRepository hotelimgRepository;

    public cafeController(ShopLikeService shopLikeService, HotelimgRepository hotelimgRepository) {
        this.shopLikeService = shopLikeService;
        this.hotelimgRepository = hotelimgRepository;
    }


    @GetMapping("cafe_list")   //카페 목록 가져오기
    public String viewList(Model model, HttpServletRequest req) {

        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);


        List<cafe> cafeList = cafeService.findByshoptype(2L);
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {
            boolean shopLike = shopLikeService.islike(cafe.getShopid(), userid);
            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(cafe.getShopid());
            String path = "";
            if (hotelimg.isPresent()) {
                path = hotelimg.get().getPath();
            } else {
                path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
            }
            cafeDTOLists.add(new CafeDTOList(cafe, shopLike, path, shopLikeService.getLikeCount(cafe.getShopid())));
        }

        model.addAttribute("cafeDTOLists", cafeDTOLists);
        return "cafe_list";
    }


    @GetMapping("cafeinfo") //카페 상세정보
    public String showInfo(Model model, HttpServletRequest req, @RequestParam("shopid") Long shopid) {
        log.info("id= " + shopid);
        Optional<cafe> cafe = cafeService.findById(shopid);
        cafe cafeinfo = cafe.get();
        model.addAttribute("cafe", cafeinfo);
        log.info(cafe.toString());

        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);
        //리뷰 리스트
        List<shopreview> shopreviewList = reviewService.findByshopid(shopid);
        model.addAttribute("shopreview", shopreviewList);

        float scoreTotal = 0;
        float scoreAvg = 0;
        for (shopreview shopreview : shopreviewList) {
            scoreTotal += shopreview.getScore();
            System.out.println("scoreTotal = " + scoreTotal);
        }
        scoreAvg = scoreTotal / shopreviewList.size();
        model.addAttribute("scoreAvg", scoreAvg);
        log.info(shopreviewList.toString());

        Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopid);
        String path = hotelimg.get().getPath();
        model.addAttribute("shopimg", path);

        return "cafeinfo";

    }

    @GetMapping("Restaurant-list")
    public String viewRestList(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        List<cafe> cafeList = cafeService.findByshoptype(3L);
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {
            boolean shopLike = shopLikeService.islike(cafe.getShopid(), userid);
            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(cafe.getShopid());
            String path = "";
            if (hotelimg.isPresent()) {
                path = hotelimg.get().getPath();
            } else {
                path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
            }
            cafeDTOLists.add(new CafeDTOList(cafe, shopLike, path, shopLikeService.getLikeCount(cafe.getShopid())));
        }
        model.addAttribute("cafeList", cafeDTOLists);
        return "Restaurant-list";
    }

    @GetMapping("restaurant-info")
    public String showRestaurantInfo(Model model, HttpServletRequest req, @RequestParam("shopid") Long shopid) {
        log.info("id= " + shopid);
        Optional<cafe> cafe = cafeService.findById(shopid);
        cafe cafeinfo = cafe.get();
        model.addAttribute("cafe", cafeinfo);
        log.info(cafe.toString());

        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);
        //리뷰 리스트
        List<shopreview> shopreviewList = reviewService.findByshopid(shopid);
        model.addAttribute("shopreview", shopreviewList);
        log.info(shopreviewList.toString());
        if (!shopreviewList.isEmpty()) {
            float scoreTotal = 0;
            float scoreAvg = 0;
            for (shopreview shopreview : shopreviewList) {
                scoreTotal += shopreview.getScore();
                System.out.println("scoreTotal = " + scoreTotal);
            }
            scoreAvg = scoreTotal / shopreviewList.size();
            model.addAttribute("scoreAvg", scoreAvg);
            log.info(shopreviewList.toString());

            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopid);
            String path = hotelimg.get().getPath();
            model.addAttribute("shopimg", path);

        }
        return "restaurant-info";

    }


}
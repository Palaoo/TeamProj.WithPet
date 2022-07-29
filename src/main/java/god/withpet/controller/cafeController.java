package god.withpet.controller;

import god.withpet.entity.cafe;
import god.withpet.entity.shoplike;
import god.withpet.entity.shopreview;
import god.withpet.repository.cafeRepository;
import god.withpet.repository.shopreviewRepository;
import god.withpet.service.ShopLikeService;
import god.withpet.service.cafeService;
import god.withpet.service.reviewService;
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

    public cafeController(ShopLikeService shopLikeService) {
        this.shopLikeService = shopLikeService;
    }


    @GetMapping("cafe_list")   //카페 목록 가져오기
    public String viewList(Model model, HttpServletRequest req) {

        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        List<cafe> cafeList = cafeService.findByshoptype(2L);
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {
            boolean shopLike = shopLikeService.islike(cafe.getShopid(),userid);
            cafeDTOLists.add(new CafeDTOList(cafe,shopLike));
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
        log.info(shopreviewList.toString());
        return "cafeinfo";

    }

    @GetMapping("Restaurant-list")
    public String viewRestList(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        List<cafe> cafeList = cafeService.findByshoptype(3L);
        model.addAttribute("cafeList", cafeList);
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
        return "restaurant-info";

    }


}
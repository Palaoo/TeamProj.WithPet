package god.withpet.controller;

import god.withpet.entity.cafe;
import god.withpet.entity.region;
import god.withpet.entity.shopreview;
import god.withpet.repository.cafeRepository;
import god.withpet.service.cafeService;
import god.withpet.service.reviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


//    @GetMapping("cafe_list")
//    public String showCafe(Model model) {
//        List<cafe> cafeEntityList = cafeRepository.findAll();
//        model.addAttribute("cafeList", cafeEntityList);
//        log.info("From cafeController showCafe(), "+cafeEntityList.toString());
//        return "cafe_list";
//    }


    @GetMapping("cafe_list")
    public String viewList(Model model) {
        List<cafe> cafeList = cafeService.findAll();
        model.addAttribute("cafeList", cafeList);
        return "cafe_list";
    }

    @GetMapping("cafeinfo")
    public String showInfo(Model model, @RequestParam("shopid") Long shopid) {
        log.info("id= " + shopid);
        Optional<cafe> cafe = cafeService.findById(shopid);
//        List<cafe> cafeList = cafeService.findById(shopid);
        cafe cafeinfo = cafe.get();
        model.addAttribute("cafe", cafeinfo);
//        model.addAttribute("cafeList",cafeList);
        log.info(cafe.toString());
        return "cafeinfo";
    }

    @GetMapping("cafeinfo")
    public String showReview(@RequestParam("shopid") Long shopid, Model model) {
        log.info("id= "+ shopid);
//        List<shopreview> shopreviewList = reviewService.findAll();
        Optional<shopreview> shopreview = reviewService.findById(shopid);
        shopreview shopreviewList = shopreview.get();

        model.addAttribute("shopriview", shopreviewList);
        log.info(shopreviewList.toString());
        return "cafeinfo";
    }

}
package god.withpet.controller;

import god.withpet.dto.reviewDto;
import god.withpet.entity.cafe;
import god.withpet.entity.region;
import god.withpet.entity.shopreview;
import god.withpet.repository.cafeRepository;
import god.withpet.repository.shopreviewRepository;
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

    @Autowired
    private shopreviewRepository shopreviewRepository;


//    @GetMapping("cafe_list")
//    public String showCafe(Model model) {
//        List<cafe> cafeEntityList = cafeRepository.findAll();
//        model.addAttribute("cafeList", cafeEntityList);
//        log.info("From cafeController showCafe(), "+cafeEntityList.toString());
//        return "cafe_list";
//    }


    @GetMapping("cafe_list")
    public String viewList(Model model) {
        List<cafe> cafeList = cafeService.findByshoptype(2L);
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
        List<shopreview> shopreviewList = reviewService.findByshopid(shopid);
        model.addAttribute("shopreview",shopreviewList);
        log.info(shopreviewList.toString());
        return "cafeinfo";
    }

    @PostMapping("cafeinfo")
    public String createReview(reviewDto dto) {
        shopreview shopreview = dto.toEntity();
        shopreview saved = shopreviewRepository.save(shopreview);
        log.info(saved.toString());
        return "";
    }
//    @GetMapping("cafeinfo/review")
//    public String showReview(@RequestParam("shopid") Long shopid, Model model) {
//        log.info("id= "+ shopid);
//        List<shopreview> shopreview = reviewService.findByshopid(shopid);
//        model.addAttribute("shopreviewList", shopreview);
//        log.info(shopreview.toString());
//        return "";

//    @GetMapping("cafeinfo/review")
//    public String ReviewList(Model model,@RequestParam) {
//        List<shopreview> shopreviewList = reviewService.findAll();
//        model.addAttribute("shopreviewList", shopreviewList);
//        log.info(shopreviewList.toString());
//        return "cafeinfo";
//    }

}
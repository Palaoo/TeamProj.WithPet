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




    @GetMapping("cafe_list")   //카페 목록 가져오기
    public String viewList(Model model) {
        List<cafe> cafeList = cafeService.findByshoptype(2L);
        model.addAttribute("cafeList", cafeList);
        return "cafe_list";
    }

    @GetMapping("cafeinfo") //카페 상세정보
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




}
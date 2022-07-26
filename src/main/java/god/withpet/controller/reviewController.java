package god.withpet.controller;

import god.withpet.dto.reviewDto;
import god.withpet.entity.shopreview;
import god.withpet.repository.shopreviewRepository;
import god.withpet.service.cafeService;
import god.withpet.service.reviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class reviewController {

    @Autowired
    private reviewService reviewService;

    @Autowired
    private cafeService cafeService;

    @Autowired
    private shopreviewRepository shopreviewRepository;

    @PostMapping("/reviews/create")  //리뷰 등록
    public String createReview(reviewDto dto,@RequestParam ("shopid") Long shopid,Model model) {

        System.out.println(dto.toString());
        shopreview shopreview = dto.toEntity();
        System.out.println(shopreview.toString());

        shopreview saved = shopreviewRepository.save(shopreview);
        System.out.println(saved.toString());
        return "redirect:/cafeinfo?shopid="+ saved.getShopid();
    }

    @GetMapping("/reviews/delete") //리뷰 삭제
    public String delete(reviewDto dto,@RequestParam("rid") Long rid, Long shopid){
        reviewService.deleteReview(rid);
        shopreview shopreview = dto.toEntity();
        log.info(shopreview.toString());
        return "redirect:/cafeinfo?shopid="+ dto.getShopid();
    }

    @PostMapping("/reviews/update")
    public String update(reviewDto dto) {
        log.info("수정 데이터"+dto.toString());
        shopreview shopreview = dto.toEntity();
        System.out.println(shopreview.getRid());
        shopreview target = shopreviewRepository.findById(shopreview.getRid()).orElse(null);
        if (target != null) {
            shopreviewRepository.save(shopreview);
        }
        return "redirect:/cafeinfo?shopid="+ shopreview.getShopid();
    }

//    @PostMapping("reviews/delete")
//    public String delete(@RequestParam ("rid") Long rid) {
//        Optional<shopreview> shopreview = shopreviewRepository.findById(rid);
//        log.info(shopreview.toString());
//        if(shopreview != null) {
//            shopreviewRepository.delete();
//
//        }
//
//        return "";
//    }

//    @PostMapping("reviews/update")
//    public String update(@RequestParam String userid, reviewDto dto) {
//        log.info(dto.toString());
//        shopreview shopreview = dto.toEntity();
//        Optional<shopreview> target = shopreviewRepository.findByUserid(shopreview.getUserid());
//        if (target != null) {
//            shopreviewRepository.save(shopreview);
//        }
//
//        return "";
//    }
}


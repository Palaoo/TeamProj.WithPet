package com.project.withpet.controller;

import com.project.withpet.dto.reviewDto;
import com.project.withpet.domain.shopreview;
import com.project.withpet.repository.shopreviewRepository;
import com.project.withpet.service.cafeService;
import com.project.withpet.service.reviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String createReview(reviewDto dto, HttpServletRequest req, @RequestParam ("shopid") Long shopid, Model model) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userid");
        System.out.println("userid = "+userid);
        if (userid != null) {
            dto.setUserid(userid);
        }

        System.out.println("테스트" + dto.toString());
        shopreview shopreview = dto.toEntity();
        System.out.println("테스트2" + shopreview.toString());


        shopreview saved = shopreviewRepository.save(shopreview);
        System.out.println("테스트3" + saved.toString());
        return "redirect:/cafeinfo?shopid="+ saved.getShopid();
    }

    @GetMapping("/reviews/delete") //리뷰 삭제
    public String delete(reviewDto dto,@RequestParam("rid") Long rid, Long shopid){
        reviewService.deleteReview(rid);
        shopreview shopreview = dto.toEntity();
        log.info(shopreview.toString());
        return "redirect:/cafeinfo?shopid="+ dto.getShopid();
    }

    @PostMapping("/reviews/update")  //리뷰 수정
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

    //리뷰 리스트 가져오기
    @GetMapping("/mypage/review")
    public String showReviewList(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userid");
        model.addAttribute("userid",userid);

        List<shopreview> shopreviewList = reviewService.findByuserid(userid);
        model.addAttribute("shopreview",shopreviewList);
        log.info("리뷰리스트 = "+ shopreviewList.toString());
        return "myreview";
    }

    @GetMapping("/mypage/review/delete") //마이페이지 리뷰 삭제
    public String deleteReview(reviewDto dto, @RequestParam("rid") Long rid, Long shopid) {
        reviewService.deleteReview(rid);
        shopreview shopreview = dto.toEntity();
        return "redirect:/mypage/review";
    }

    @PostMapping("/mypage/review/update") //마이페이지 리뷰 수정
    public String updateReview(reviewDto dto) {
        shopreview shopreview = dto.toEntity();
        shopreview target = shopreviewRepository.findById(shopreview.getRid()).orElse(null);
        if (target != null) {
            shopreviewRepository.save(shopreview);
        }
        return "redirect:/mypage/review";
    }

}


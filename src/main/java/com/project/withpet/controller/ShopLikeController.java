
package com.project.withpet.controller;


import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shoplike;
import com.project.withpet.dto.LikeshopDTO;
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

    public ShopLikeController(ShopLikeService shopLikeService, com.project.withpet.service.cafeService cafeService, LikeShopRepository likeshopRepository) {
        this.shopLikeService = shopLikeService;
        this.cafeService = cafeService;
        this.likeshopRepository = likeshopRepository;
    }

    @GetMapping("/mypage/myshop")  //마이페이지 좋아요 리스트
    public String showMyshop(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userLogined", userid);

        List<shoplike> shoplikeList = shopLikeService.findByUserId(userid);
        List<LikeshopDTO> likeshopDTOList = new ArrayList<>();
        for (int i = 0; i < shoplikeList.size(); i++) {
            shoplike shoplike = shoplikeList.get(i);
            log.info("shoplikelist = " + shoplike.toString());
            Optional<cafe> cafe = cafeService.findById(shoplike.getShopid());
            likeshopDTOList.add(new LikeshopDTO(shoplike, cafe.get()));
        }

        model.addAttribute("likeshopDTOList",likeshopDTOList);
        log.info("좋아요 리스트 = " + likeshopDTOList.toString());
        return "myshop";
    }

    @GetMapping("/mypage/myshop/delete") //좋아요 삭제
    public String deleteMyShop(LikeshopDTO dto, @RequestParam("likeid") Long likeid, Long shopid) {
        likeshopRepository.deleteById(likeid);
        return "redirect:/mypage/myshop";
    }


    @GetMapping("append_shoplike")
    @ResponseBody
    public String appendShopLike(HttpServletRequest req, @RequestParam Long shopId) {
        System.out.println("ShopLikeController appendShopLike(), , shopId");
        if (shopLikeService.saveordelete(req.getSession().getAttribute("userLogined").toString(),
                shopId) == 0){
            return "0";
        }

            return "1";
    }


}

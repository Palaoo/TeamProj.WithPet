package com.project.withpet.controller;

import com.project.withpet.service.ProdReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProdReviewController {
    private final ProdReviewService prodReviewService;
    private Tools tools = new Tools();

    public ProdReviewController(ProdReviewService prodReviewService) {
        this.prodReviewService = prodReviewService;
    }

    @GetMapping("save_prodReview")
    public String saveProdReview(@RequestParam Long prodId, @RequestParam String text, @RequestParam int star,
                                 HttpServletRequest req) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        String userId = req.getSession().getAttribute("userLogined").toString();

        prodReviewService.saveProdReview(prodId, userId, text, star);

        return "redirect:prod_view?prodId=" + prodId;

    }

    @GetMapping("delete_prodReview")
    public String deleteProdReview(@RequestParam Long prodId, HttpServletRequest req) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        prodReviewService.deleteProdReview(prodId, req.getSession().getAttribute("userLogined").toString());

        return "redirect:prod_view?prodId=" + prodId;
    }
}

package com.project.withpet.controller;

import com.project.withpet.dto.BasketDTO;
import com.project.withpet.service.BasketService;
import com.project.withpet.service.BusinessUserService;
import com.project.withpet.service.ImgService;
import com.project.withpet.service.ProdService;
import com.project.withpet.domain.Basket;
import com.project.withpet.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BasketController {
    private final BasketService basketService;
    private final ProdService prodService;
    private final ImgService imgService;
    private final BusinessUserService businessUserService;
    private final Tools tools = new Tools();

    public BasketController(BasketService basketService, ProdService prodService, ImgService imgService,
                            BusinessUserService businessUserService) {
        this.basketService = basketService;
        this.prodService = prodService;
        this.imgService = imgService;
        this.businessUserService = businessUserService;
    }

    @GetMapping("basket_view")
    public String basket_view(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }

        String userId = req.getSession().getAttribute("userid").toString();
        List<Basket> basketList = basketService.findByUserid(userId);
        ArrayList<BasketDTO> basketDTOS = new ArrayList<>();
        for (Basket basket : basketList) {
            Product prod = prodService.findById(basket.getProdId()).get();
            String brand = businessUserService.findByBid(prod.getBid()).getBrand();
            basketDTOS.add(new BasketDTO(basket.getProdId(), prod.getName(), prod.getPrice(), imgService.findByProdid(basket.getProdId()).get().getPath(), brand));
        }


        model.addAttribute("BasketDTOs", basketDTOS);
        model.addAttribute("userLogined", userId);
        return "basket_view";
    }

    @GetMapping("append_basket")
    @ResponseBody
    public String append_basket(@RequestParam Long prodId, HttpServletRequest req) {
        try {
            basketService.appendBasket(prodId, req.getSession().getAttribute("userid").toString());

        } catch (IllegalStateException e) {
            return "0";
        }
        return "1";
    }

    @GetMapping("delete_basket")
    public String delete_basket(HttpServletRequest req,@RequestParam Long prodId) {
        basketService.deleteBasket(prodId, req.getSession().getAttribute("userid").toString());
        return "redirect:/basket_view";
    }

}

package com.project.withpet.controller;

import com.project.withpet.domain.Shop;
import com.project.withpet.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/hotel")
    public String hotelList(Model model, HttpServletRequest req){

        List<Shop> shops = shopService.shopList();
        model.addAttribute("shops", shops);

        shops.get(1).getShopFeats().get(1).getFeatname();

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        return "shop/hotel";
    }


}

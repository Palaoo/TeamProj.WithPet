package com.project.withpet.controller;

import com.project.withpet.repository.ShopRepositoryImpl;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopController {

    private final ShopRepositoryImpl shopRepositoryImpl;

    @Autowired
    public ShopController(ShopRepositoryImpl shopRepositoryImpl) {
        this.shopRepositoryImpl = shopRepositoryImpl;
    }

    @GetMapping("/hotel")
    public String hotelList(Model model, HttpServletRequest req){


        List<Tuple> shops = shopRepositoryImpl.findAll();
        model.addAttribute("shops", shops);
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "shop/hotel";
    }

}

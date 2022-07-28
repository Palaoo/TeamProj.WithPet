package com.project.withpet.controller;

import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MarketController {

    @GetMapping("mall")
    public String doMall(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "market/mall";
    }

    @GetMapping("/myshopping")
    public String doMyshopping(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "market/myshopping";
    }

    @GetMapping("/basket_view")
    public String doBasket(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")==null){
            return "redirect:/login";
        } else {
            model.addAttribute("userid", session.getAttribute("userid"));
            return "market/basket_view";
        }
    }

    @GetMapping("/prod_view")
    public String doProdView(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "market/prod_view";
    }

    @GetMapping("/cafe_list")
    public String doCafeList(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "cafe/cafe_list";
    }
}

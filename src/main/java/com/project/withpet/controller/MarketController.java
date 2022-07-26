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

}

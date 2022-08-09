package com.project.withpet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class likeshopController {

//    @GetMapping("/mypage/myshop")
//    public String showMyshop(HttpServletRequest req, Model model) {
//        HttpSession session = req.getSession();
//        String userid = (String) session.getAttribute("userLogined");
//        model.addAttribute("userLogined", userid);
//        return "myshop";
//    }
}

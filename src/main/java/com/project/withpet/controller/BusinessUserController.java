package com.project.withpet.controller;

import com.project.withpet.service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BusinessUserController {
    private final BusinessUserService businessUserService;
    private final Tools tools=new Tools();

    @Autowired
    public BusinessUserController(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    @GetMapping("/businessPage")
    public String businessPage(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }

        if (businessUserService.isBusinessUser(req.getSession().getAttribute("userid").toString()) == -1L) {
            return "registBusiness";
        }
        return "redirect:/businessInfo";
    }

    @GetMapping("/registBusiness")
    public String registBusiness(HttpServletRequest req) {
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 시작");
        businessUserService.join(req.getSession().getAttribute("userid").toString());
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 완료");
        return "redirect:/businessInfo";
    }

    @GetMapping("/businessInfo")
    public String businessInfo(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }
        model.addAttribute("businessId", businessUserService.findByUid(req.getSession().getAttribute("userid").toString()).getBid());
        req.getSession().setAttribute("businessId", model.getAttribute("businessId").toString());
        return "businessInfo";
    }

    @GetMapping("appendProd")
    public String appendProd() {
        return "newProd";
    }

//    @GetMapping("newProd")
//    public String newProd(HttpServletRequest req) {
//
//    }

}

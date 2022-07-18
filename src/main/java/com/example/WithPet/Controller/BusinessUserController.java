package com.example.WithPet.Controller;

import com.example.WithPet.Service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BusinessUserController {
    private final BusinessUserService businessUserService;
    private final Tools tools = new Tools();

    @Autowired
    public BusinessUserController(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    @GetMapping("/businessPage")
    public String businessPage(HttpServletRequest req, Model model) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        if (businessUserService.isBusinessUser(req.getSession().getAttribute("userLogined").toString()) == -1L) {
            return "registBusiness";
        }
        return "redirect:/businessInfo";
    }

    @GetMapping("/registBusiness")
    public String registBusiness(HttpServletRequest req) {
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 시작");
        businessUserService.join(req.getSession().getAttribute("userLogined").toString());
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 완료");
        return "redirect:/businessInfo";
    }

    @GetMapping("/businessInfo")
    public String businessInfo(HttpServletRequest req, Model model) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }
        model.addAttribute("businessId", businessUserService.findByUid(req.getSession().getAttribute("userLogined").toString()).getBid());
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

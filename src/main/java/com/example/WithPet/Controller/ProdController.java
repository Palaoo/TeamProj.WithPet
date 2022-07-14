package com.example.WithPet.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProdController {
    private final Tools tools = new Tools();

    @GetMapping("/mallPage")
    public String mallPage(HttpServletRequest req, Model model) {
        if (tools.isUserLogined(req)) {
            model.addAttribute("userLogined", req.getSession().getAttribute("userLogined"));
        }

        return "mall";
    }

    @GetMapping("/userInfoPage")
    public String userInfo(HttpServletRequest req, Model model) {
        if (tools.isUserLogined(req)) {
            model.addAttribute("userLogined", req.getSession().getAttribute("userLogined"));
        }

        return "userInfo";
    }

    @GetMapping("/businessPage")
    public String businessPage(HttpServletRequest req, Model model) {
        if (tools.isUserLogined(req)) {
            model.addAttribute("userLogined", req.getSession().getAttribute("userLogined"));
        }

        return "";
    }
}

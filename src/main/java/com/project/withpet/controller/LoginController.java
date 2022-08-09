package com.project.withpet.controller;

import com.project.withpet.domain.User;
import com.project.withpet.dto.UserForm;
import com.project.withpet.service.BusinessUserService;
import com.project.withpet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginController {

    private final UserService userService;
    private final BusinessUserService businessUserService;

    @Autowired
    public LoginController(UserService userService, BusinessUserService businessUserService) {
        this.userService = userService;
        this.businessUserService = businessUserService;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "home";
    }

    @GetMapping(value = "signup")
    public String createForm() {
        return "signup";
    }


    @PostMapping(value = "signup")
    public String create(UserForm form) {
        User user = new User();
        user.setUserId(form.getUserid());
        user.setMobile(form.getMobile());
        user.setName(form.getName());
        user.setPassword(form.getPassword());

        userService.join(user);

        return "redirect:/";
    }

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    public String login(@RequestParam("userid") String userid,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletRequest req) {
        boolean result = userService.checkUser(userid, password);
        if (result == true) {
            HttpSession session = req.getSession();
            session.setAttribute("userid", userid);
            session.setAttribute("userLogined", userid);
            if (businessUserService.isBusinessUser(req.getSession().getAttribute("userid").toString()) != -1L) {
                model.addAttribute("businessId", businessUserService.findByUid(req.getSession().getAttribute("userid").toString()).getBid());
                req.getSession().setAttribute("businessId", model.getAttribute("businessId").toString());

            }

            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

//    @GetMapping("logout")
//    public String logout(HttpServletRequest req){
//        HttpSession session = req.getSession();
//        session.removeAttribute("userLogined");
//        return "redirect:/";
//    }

    @PostMapping("checkuser")
    @ResponseBody
    public boolean checkuser(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            return true;
        } else {
            return false;
        }
    }


}

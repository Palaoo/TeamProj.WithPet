package com.project.withpet.controller;

import com.project.withpet.domain.User;
import com.project.withpet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "signup")
    public String createForm() {
        return "signup";
    }

    @PostMapping(value = "signup")
    public String create(UserForm form){
        User user = new User();
        user.setUserId(form.getUserId());
        user.setMobile(form.getMobile());
        user.setName(form.getName());
        user.setPassword(form.getPassword());

        userService.join(user);

        return "redirect:/";
    }

    @GetMapping(value = "login")
    public String login(){
        return "login";
    }

    @PostMapping (value = "login")
    public String login(@RequestParam("userid") String userid,
                        @RequestParam("password") String password,
                        Model model,
                        HttpServletRequest req) {
        boolean result = userService.checkUser(userid, password);
        if(result==true){
            HttpSession session = req.getSession();
            session.setAttribute("userid", userid);
            return "redirect:/";
        } else {
            return "login";
        }

    }



}

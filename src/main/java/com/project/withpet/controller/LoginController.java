package com.project.withpet.controller;

import com.project.withpet.domain.User;
import com.project.withpet.dto.UserForm;
import com.project.withpet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        return "home";
    }

    @GetMapping(value = "signup")
    public String createForm() {
        return "signup";
    }


    @PostMapping(value = "signup")
    public String create(@ModelAttribute @Valid UserForm form, BindingResult errors, Model model){
        /*if (errors.hasErrors()) {
            model.addAttribute("userDto", form);
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "redirect:/signup";

        }*/
        /*if (errors.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "redirect:/signup";
        }*/

        User user = new User();
        user.setUserId(form.getUserid());
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
            session.setAttribute("userLogined", userid);
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
    public boolean checkuser(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            return true;
        } else {
            return false;
        }
    }



}

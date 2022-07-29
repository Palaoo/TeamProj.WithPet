package com.project.withpet.controller;

import com.project.withpet.domain.User;
import com.project.withpet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {
    private final Tools tools = new Tools();
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


//    @GetMapping("/loginPage")
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping("/signupPage")
    public String signup() {
        return "signup";
    }

    @PostMapping("/appendUser")
    @ResponseBody
    public String appendUser(HttpServletRequest req) {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");
        System.out.println(id + password + name + mobile);
        User user = new User();
        user.setUserId(id);
        user.setPassword(password);
        user.setName(name);
        user.setMobile(mobile);

        try {
            userService.join(user);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "1";
        }


        return "0";
    }

    @GetMapping("/validId")
    @ResponseBody
    public String validId(HttpServletRequest req) {
        String id = req.getParameter("id");
        System.out.println(id);
        User user = new User();
        user.setUserId(id);

        try {
            userService.validDuplicateUser(user);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "1";
        }

        return "0";
    }

//    @GetMapping("/")
//    public String home(HttpServletRequest req, Model model) {
//        if (!tools.isUserLogined(req)) {
//            return "login";
//        }
//        model.addAttribute("userLogined", req.getSession().getAttribute("userLogined"));
//        return "home";
//    }

//    @PostMapping("/login")
//    public String login(User user, HttpServletRequest req) {
//        if (userService.login(user)) {
//            HttpSession session = req.getSession();
//            session.setAttribute("userLogined", user.getUserId());
//            return "redirect:/";
//        }
//        return "login";
//    }

//    @GetMapping("logout")
//    public String logout(HttpServletRequest req) {
//        req.getSession().invalidate();
//        return "redirect:/";
//    }

}
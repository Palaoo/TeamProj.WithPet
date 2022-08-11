package com.project.withpet.controller;

import com.project.withpet.domain.User;
import com.project.withpet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {
    private final Tools tools = new Tools();
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/signupPage")
    public String signup() {
        return "signup";
    }

    @PostMapping("/users/update")
    public String appendKakaoEmail(@RequestParam(value = "userId") String userId,HttpServletRequest req,
                                   @RequestParam(value = "kakaoEmail") String kakaoEmail) {
        userService.updateKakaoEmail(userId, kakaoEmail);
        req.getSession().setAttribute("userid",userId);
        req.getSession().setAttribute("userLogined",userId);

        return "redirect:/";
    }

    @PostMapping("/appendUser")
    @ResponseBody
    public String appendUser(HttpServletRequest req, @RequestParam(value = "kakaoEmail") String kakaoEmail) {
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
        if (kakaoEmail != "undefined" && kakaoEmail != "" && kakaoEmail != null) {
            user.setKakaoEmail(kakaoEmail);
        }

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

    @GetMapping("logout")
    public String logout(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("mypage")
    public String myPage(Model model, HttpServletRequest req) {
        model.addAttribute("userid", req.getSession().getAttribute("userid").toString());
        return "mypage";
    }

}

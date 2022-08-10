package com.project.withpet.controller;

import com.project.withpet.domain.User;
import com.project.withpet.dto.UserForm;
import com.project.withpet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
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
    public String createForm(@RequestParam(value = "kakaoEmail", required = false) String kakaoEmail,
                             Model model) {
        model.addAttribute("kakaoEmail", kakaoEmail);
        return "signup";
    }


    @PostMapping(value = "signup")
    public String create(@ModelAttribute @Valid UserForm form, BindingResult errors, Model model) {
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

    @RequestMapping(value = "/kakao-users", method = RequestMethod.GET)
    public String kakaoUsers(@RequestParam(value = "code", required = false) String code, HttpServletRequest req,
                             Model model) {
        System.out.println("######## " + code);

        String access_Token = userService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);

        HashMap<String, Object> userInfo = userService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###email#### : " + userInfo.get("email"));
        String kakaoEmail = userInfo.get("email").toString();
        int result = userService.kakaoUserValidation(kakaoEmail);

        HttpSession session = req.getSession();

        if (result == 1) {   // 1( kakao email과 동일 ) -> 해당 아이디로 로그인
            String userId = userService.findByKakaoEmail(kakaoEmail).get().getUserId();
            session.setAttribute("userid", userId);
            session.setAttribute("userLogined", userId);
            return "redirect:/";
        } else if (result == 2) {    // 2( kakao email과 동일하지 않지만, userid와 동일 -> 이 아이디가 본인 아이디인지 묻는다.
            model.addAttribute("kakaoEmail", kakaoEmail);
            model.addAttribute("userId", userService.findById(kakaoEmail).get().getUserId());
            return "kakao-login-valid";
        } else { // 0( kakao email과 동일하지 않고, userid도 없음 ) -> 회원가입으로
            return "signup?kakaoEmail=" + kakaoEmail;
        }

    }


}

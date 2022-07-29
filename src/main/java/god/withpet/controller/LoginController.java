package god.withpet.controller;

import god.withpet.dto.UserForm;
import god.withpet.entity.User;
import god.withpet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    private UserService userService;

    @GetMapping(value = "signup")
    public String createForm() {
        return "signup";
    }

    @PostMapping(value = "signup")
    public String create(UserForm form){
        User user = new User();
        user.setUserid(form.getUserid());
        user.setMobile(form.getMobile());
        user.setName(form.getName());
        user.setPassword(form.getPassword());

        userService.join(user);

        return "redirect:/login";
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
        log.info(userService.toString());
        if(result==true){
            HttpSession session = req.getSession();
            session.setAttribute("userid", userid);
            return "redirect:/";
        } else {
            return "redirect:/signup";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("userid");
        return "redirect:/";
    }



}

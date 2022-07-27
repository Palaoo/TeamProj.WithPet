package god.withpet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class HomeController {

    @GetMapping("/")
    public String viewHome(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userid");
        model.addAttribute("userid",userid);
        return "home";
    }
}

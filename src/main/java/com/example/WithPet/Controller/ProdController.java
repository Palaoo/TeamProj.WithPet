package com.example.WithPet.Controller;

import com.example.WithPet.Service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProdController {
    private final Tools tools = new Tools();


    @GetMapping("/mallPage")
    public String mallPage(HttpServletRequest req, Model model) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        return "mall";
    }

    @GetMapping("/userInfoPage")
    public String userInfo(HttpServletRequest req, Model model) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        return "userInfo";
    }


}

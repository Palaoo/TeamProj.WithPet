package com.example.WithPet.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("prod_view")
    public String prod_view(Model model) {
        return "prod_view";
    }

    @GetMapping("mall")
    public String mall(Model model) {
        return "mall";
    }

    @GetMapping("test")
    public String test() {
        return "testpage";
    }

    @GetMapping("ref")
    public String ref() {
        return "ref";
    }

    @GetMapping("appendProd")
    public String appendProd() {
        return "appendProd";
    }
}

package com.example.WithPet.Controller;

import com.example.WithPet.Service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessUserController {
    private final BusinessUserService businessUserService;

    @Autowired
    public BusinessUserController(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    @GetMapping
    public

}

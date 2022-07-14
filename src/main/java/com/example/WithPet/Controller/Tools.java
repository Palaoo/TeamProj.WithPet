package com.example.WithPet.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Tools {
    public boolean isUserLogined(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userLogined") != null)
            return true;
        return false;
    }
}

package com.project.withpet.controller;

import com.project.withpet.domain.Hotelimg;
import com.project.withpet.domain.Shop;
import com.project.withpet.dto.HotelForm;
import com.project.withpet.repository.HotelimgRepository;
import com.project.withpet.service.BusinessUserService;
import com.project.withpet.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BusinessUserController {
    private final BusinessUserService businessUserService;
    private final Tools tools = new Tools();
    private final ShopService shopService;
    private final HotelimgRepository hotelimgRepository;

    @Autowired
    public BusinessUserController(BusinessUserService businessUserService, ShopService shopService, HotelimgRepository hotelimgRepository) {
        this.businessUserService = businessUserService;
        this.shopService = shopService;
        this.hotelimgRepository = hotelimgRepository;
    }

    @GetMapping("/businessPage")
    public String businessPage(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }

        if (businessUserService.isBusinessUser(req.getSession().getAttribute("userid").toString()) == -1L) {
            return "registBusiness";
        }
        return "redirect:/businessInfo";
    }

    @GetMapping("/registBusiness")
    public String registBusiness(HttpServletRequest req) {
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 시작");
        businessUserService.join(req.getSession().getAttribute("userid").toString());
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 완료");
        return "redirect:/businessInfo";
    }

    @GetMapping("/businessInfo")
    public String businessInfo(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }
        model.addAttribute("businessId", businessUserService.findByUid(req.getSession().getAttribute("userid").toString()).getBid());
        req.getSession().setAttribute("businessId", model.getAttribute("businessId").toString());
        return "businessInfo";
    }

    @GetMapping("/shopInfo")
    public String shopInfo(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }
        List<Shop> shops = shopService.findAllByBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));
        System.out.println("shops size = " + shops.size());
        List<HotelForm> hotelForms = new ArrayList<>();
        for (Shop shop : shops) {
            Optional<Shop> shopOptional = shopService.findById(shop.getShopid());
            Optional<Hotelimg> hotelimgOptional = hotelimgRepository.findByShopid(shop.getShopid());
            hotelForms.add(new HotelForm(hotelimgOptional.get().getPath(), shopOptional.get()));
        }

        model.addAttribute("shopForms", hotelForms);

        return "shopInfo";
    }

    @GetMapping("/shopInfo/deleteShop")
    public String deleteShop(@RequestParam Long shopid, @RequestParam Long typeid) {

        shopService.deleteById(shopid);
        return "redirect:/shopInfo";
    }

    @GetMapping("appendProd")
    public String appendProd() {
        return "newProd";
    }

    @GetMapping("/appendShop")
    public String appendShop() {
        return "newShop";
    }

}

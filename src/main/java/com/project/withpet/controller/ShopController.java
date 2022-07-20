package com.project.withpet.controller;

import com.project.withpet.domain.QRegion;
import com.project.withpet.domain.QShop;
import com.project.withpet.domain.Shop;
import com.project.withpet.domain.ShopForm;
import com.project.withpet.repository.ShopQueryRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {

    private final ShopQueryRepository shopQueryRepository;

    @Autowired
    public ShopController(ShopQueryRepository shopQueryRepository) {
        this.shopQueryRepository = shopQueryRepository;
    }

    @GetMapping("/hotel")
    public String hotelList(Model model, HttpServletRequest req){

//        Shop shop = shopQueryRepository.findByName();
        List<Shop> shopList = shopQueryRepository.findShopInShop();
        model.addAttribute("shopList", shopList);

//        List<Tuple> shops = shopQueryRepository.findAllShops();
//        List<ShopForm> shopAvail = new ArrayList<>();
//
//        for(Tuple row : shops){
//            ShopForm shopForm = new ShopForm();
//            shopForm.setName(row.get(QShop.shop.name));
//            shopForm.setRegname(row.get(QRegion.region.regname));
//            shopAvail.add(shopForm);
//            model.addAttribute("name", row.get(QShop.shop.name));
//            model.addAttribute("regname", row.get(QRegion.region.regname));
//        }
//        System.out.println(shopAvail);
//        model.addAttribute("hotels", shopAvail);


        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        return "shop/hotel";
    }


}

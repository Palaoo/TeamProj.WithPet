package com.example.WithPet.Controller;

import com.example.WithPet.Service.BasketService;
import com.example.WithPet.Service.BusinessUserService;
import com.example.WithPet.Service.ImgService;
import com.example.WithPet.Service.ProdService;
import com.example.WithPet.domain.Basket;
import com.example.WithPet.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BasketController {
    private final BasketService basketService;
    private final ProdService prodService;
    private final ImgService imgService;
    private final BusinessUserService businessUserService;
    private final Tools tools = new Tools();

    public BasketController(BasketService basketService, ProdService prodService, ImgService imgService, BusinessUserService businessUserService) {
        this.basketService = basketService;
        this.prodService = prodService;
        this.imgService = imgService;
        this.businessUserService = businessUserService;
    }

    @GetMapping("basket_view")
    public String basket_view(HttpServletRequest req, Model model) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        String userId = req.getSession().getAttribute("userLogined").toString();
        List<Basket> basketList = basketService.findByUserid(userId);
        ArrayList<BascketDTO> bascketDTOs = new ArrayList<>();
        for (Basket basket : basketList) {
            Product prod = prodService.findById(basket.getProdid());
            String brand = businessUserService.findByBid(prod.getBid()).getBrand();
            bascketDTOs.add(new BascketDTO(basket.getProdid(), prod.getName(), prod.getPrice(), imgService.findByProdid(basket.getProdid()).get().getPath(), brand));
        }
        model.addAttribute("BasketDTOs", bascketDTOs);
        model.addAttribute("userLogined", userId);
        return "basket_view";
    }
}

package god.withpet.controller;

import god.withpet.service.ShopLikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ShopLikeController {
    private final ShopLikeService shopLikeService;

    public ShopLikeController(ShopLikeService shopLikeService) {
        this.shopLikeService = shopLikeService;
    }

    @GetMapping("/mypage/myshop")
    public String showMyshop(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userLogined", userid);
        return "myshop";
    }

    @GetMapping("append_shoplike")
    @ResponseBody
    public String appendShopLike(HttpServletRequest req, @RequestParam Long shopId) {
        System.out.println("ShopLikeController appendShopLike(), , shopId");
        if (shopLikeService.saveordelete(req.getSession().getAttribute("userLogined").toString(),
                shopId) == 0){
            return "0";
        }

            return "1";
    }

}

package com.example.WithPet.Controller;

import com.example.WithPet.Service.OrderService;
import com.example.WithPet.domain.Ordertable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {
    private final Tools tools = new Tools();
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/paysuccess")
    public String paySuccess(@RequestParam String paymentKey, @RequestParam String orderId,
                             @RequestParam int amount, Model model) {
//        orderService.findByDate()

        return "redirect:/myshopping";
    }

    @GetMapping("/myshopping")
    public String searchMyShopping(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
                                   HttpServletRequest req) {
        System.out.printf("From OrderController myShopping(), startDate: %s, endDate: %s", startDate, endDate);
        if (!tools.isUserLogined(req)) {
            return "login";
        }
        if (startDate != null && endDate != null) {

            LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String userId = req.getSession().getAttribute("userLogined").toString();
            List<Ordertable> findOrderList = orderService.findByDate(userId, start, end);

            for (Ordertable ordertable : findOrderList) {
                System.out.printf("From OrderController myShopping(), ordertable.orderdate: %s, userid:%s\n",
                        ordertable.getOrderdate(), ordertable.getUserid());

            }
        }
        return "myshopping";
    }


}

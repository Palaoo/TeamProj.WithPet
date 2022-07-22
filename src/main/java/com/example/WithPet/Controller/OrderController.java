package com.example.WithPet.Controller;

import com.example.WithPet.Service.ImgService;
import com.example.WithPet.Service.OrderService;
import com.example.WithPet.Service.OrderprodService;
import com.example.WithPet.Service.ProdService;
import com.example.WithPet.domain.Orderprod;
import com.example.WithPet.domain.Ordertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private final Tools tools = new Tools();
    private final OrderService orderService;
    private final OrderprodService orderprodService;
    @Autowired
    private final ProdService prodService;
    private final ImgService imgService;

    @Autowired
    public OrderController(OrderService orderService, OrderprodService orderprodService, ProdService prodService, ImgService imgService) {
        this.orderService = orderService;
        this.orderprodService = orderprodService;
        this.prodService = prodService;
        this.imgService = imgService;
    }


    @GetMapping("/paysuccess")
    public String paySuccess(@RequestParam String paymentKey, @RequestParam String orderId,
                             @RequestParam int amount,
                             @RequestParam String priceid, @RequestParam String count,
                             HttpServletRequest req) throws IOException, InterruptedException {
//        orderService.findByDate()

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\"" + paymentKey + "\",\"amount\":" + amount + ",\"orderId\":\"" + orderId + "\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        Long orderid = orderService.save(new Ordertable(req.getSession().getAttribute("userLogined").toString(), tools.getLDTnow())).getOrderid();
        orderprodService.save(new Orderprod(orderid, priceid, count));

        return "redirect:/myshopping";
    }


    @GetMapping("/myshopping")
    public String searchMyShopping(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
                                   HttpServletRequest req, Model model, @RequestParam(required = false) Integer paging) {
        System.out.printf("From OrderController myShopping(), startDate: %s, endDate: %s\n", startDate, endDate);
        if (!tools.isUserLogined(req)) {
            return "login";
        }
        if (startDate == null && endDate == null) {
            startDate = LocalDate.now().minusMonths(1).toString();
            endDate = LocalDate.now().toString();
            System.out.println(startDate + " " + endDate);
        }
        if (paging == null) {
            paging = 0;
        }

//            LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA));
//            LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.KOREA));

        List<LocalDateTime> localDateTimes = tools.convertToLDT(startDate, endDate);
        LocalDateTime start = localDateTimes.get(0);
        LocalDateTime end = localDateTimes.get(1);

        String userId = req.getSession().getAttribute("userLogined").toString();
        List<Ordertable> findOrderList = orderService.findByDate(userId, start, end, paging);

        ArrayList<String> orderdates = new ArrayList<>();
        ArrayList<OrderListDTO> orderListDTOs = new ArrayList<>();
        for (Ordertable ordertable : findOrderList) {
            System.out.printf("From OrderController myShopping(), ordertable.orderdate: %s, userid:%s\n",
                    ordertable.getOrderdate(), ordertable.getUserid());
//                orderprods.add(orderprodService.findByOrderid(ordertable.getOrderid().toString()));
            orderdates.add(ordertable.getOrderdate().toString());

//            orderprods.add(orderprodService.findByOrderid(ordertable.getOrderid().toString()));
            Orderprod orderprod = orderprodService.findByOrderid(ordertable.getOrderid());
            String[] prodIdArr = orderprod.getProdlist().split(" ");
            String[] countArr = orderprod.getCount().split(" ");
            System.out.printf("From OrderController searchMyshopping(), orderprod.getProdlist():%s, orderprod.getCount(): %s\n", orderprod.getProdlist(), orderprod.getCount());
            ArrayList<ProdandCount> prodandCounts = new ArrayList<>();

            for (int i = 0; i < prodIdArr.length; i++) {
                System.out.printf("From OrderController searchMyshopping(), countArr[%d]: %s, prodIdArr[%d]: %s\n", i, countArr[i], i, prodIdArr[i]);
                Long prodId = Long.parseLong(prodIdArr[i]);
                prodandCounts.add(
                        new ProdandCount(prodService.findById(prodId),
                                Integer.parseInt(countArr[i]),
                                imgService.findByProdid(prodId.longValue()).get().getPath())
                );

            }
//            System.out.printf("From OrderController searchMyshopping(), prod.name: %s ", prodandCounts.get(0).prod.getName());
            System.out.printf("From OrderController searchMyshopping(), prodIdArr[i]: %d\n", Long.parseLong(prodIdArr[0]));

            //            for (String prodId : prodIdArr) {
//                prods.add(prodService.findOne(Long.parseLong(prodId)).get());
//            }
//            ArrayList<Integer> countList = new ArrayList<>();
//            for (String count : countArr) {
//                countList.add(Integer.parseInt(count));
//            }

            orderListDTOs.add(new OrderListDTO(prodandCounts, ordertable));
        }

        model.addAttribute("orderDTOList", orderListDTOs);
        return "myshopping";
    }


}

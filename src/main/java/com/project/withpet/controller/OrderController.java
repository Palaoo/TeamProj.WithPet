package com.project.withpet.controller;

import com.project.withpet.dto.OrderListDTO;
import com.project.withpet.dto.ProdandCount;
import com.project.withpet.service.ImgService;
import com.project.withpet.service.OrderService;
import com.project.withpet.service.OrderprodService;
import com.project.withpet.service.ProdService;
import com.project.withpet.domain.Orderprod;
import com.project.withpet.domain.Ordertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class OrderController {
    private final Tools tools = new Tools();
    private final OrderService orderService;
    private final OrderprodService orderprodService;
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
        System.out.printf("From OrderController paySuccess(), priceid= %s, count: %s\n", priceid, count);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\"" + paymentKey + "\",\"amount\":" + amount + ",\"orderId\":\"" + orderId + "\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        Long orderid = orderService.save(new Ordertable(req.getSession().getAttribute("userid").toString(), tools.getLDTnow())).getOrderid();
        orderprodService.save(new Orderprod(orderid, priceid, count));



        return "redirect:/myshopping";
    }


    @GetMapping("/myshopping")
    public String searchMyShopping(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
                                   HttpServletRequest req, Model model, @RequestParam(required = false) Integer paging) {

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }
        System.out.printf("From OrderController myShopping(), startDate: %s, endDate: %s\n", startDate, endDate);
        if (!tools.isUserLogined(req)) {
            return "login";
        }
        if ((startDate == null && endDate == null) || (startDate == "" && endDate == "")) {
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

        String userId = req.getSession().getAttribute("userid").toString();
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
                try {
                    prodandCounts.add(
                            new ProdandCount(prodService.findById(prodId).get(),
                                    Integer.parseInt(countArr[i]),
                                    imgService.findByProdid(prodId.longValue()).get().getPath())
                    );
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    prodandCounts = null;
                }

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

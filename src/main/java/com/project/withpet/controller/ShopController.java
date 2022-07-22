package com.project.withpet.controller;

import com.project.withpet.domain.Feat;
import com.project.withpet.domain.Shop;
import com.project.withpet.repository.ShopQueryRepository;
import com.project.withpet.repository.ShopRepository;
import com.project.withpet.service.HotelroomService;
import com.project.withpet.service.ShopService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.apache.el.util.MessageFactory.get;

@Controller
public class ShopController {

    private final ShopService shopService;
    private final HotelroomService hotelroomService;
    private final ShopQueryRepository shopQueryRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public ShopController(ShopService shopService, HotelroomService hotelroomService, ShopQueryRepository shopQueryRepository, ShopRepository shopRepository) {
        this.shopService = shopService;
        this.hotelroomService = hotelroomService;
        this.shopQueryRepository = shopQueryRepository;
        this.shopRepository = shopRepository;
    }

    @GetMapping("/hotel")
    public String hotelList(Model model, HttpServletRequest req){

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        LocalDate now = LocalDate.now();
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(now.toString());
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }

        cal.setTime(date);

        int week = now.getDayOfWeek().getValue();

        if( week == 0 || week == 1 || week == 2 || week == 3 || week == 4 || week == 5 ){
            switch (week){
                case 0:
                    cal.add(Calendar.DATE, 6);
                    break;
                case 1:
                    cal.add(Calendar.DATE, 5);
                    break;
                case 2:
                    cal.add(Calendar.DATE, 4);
                    break;
                case 3:
                    cal.add(Calendar.DATE, 3);
                    break;
                case 4:
                    cal.add(Calendar.DATE, 2);
                    break;
                case 5:
                    cal.add(Calendar.DATE, 1);
                    break;
            }
        }

        String checkin = df.format(cal.getTime());
        model.addAttribute("checkin", checkin);

        cal.add(Calendar.DATE, 1);
        String checkout = df.format(cal.getTime());
        model.addAttribute("checkout", checkout);

        List<Shop> availShop = shopQueryRepository.findAvailShop(checkin, checkout, 2L);
        model.addAttribute("shops", availShop);

        return "shop/hotel";
    }

    @PostMapping("/hotel")
    public String hotelinfo(Model model,
                            HttpServletRequest req,
                            @RequestParam("person") Long person,
                            @RequestParam("checkin") String checkin,
                            @RequestParam("checkout") String checkout){

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("person", person);

        List<Shop> availShop = shopQueryRepository.findAvailShop(checkin, checkout, person);
        List<Shop> allShop = shopRepository.findAll();


        model.addAttribute("shops", availShop);



        return "shop/hotel";
    }

    @GetMapping("/hotel/detail")
    public String hotelDetail(Model model, HttpServletRequest req,
                              @RequestParam("shopid") Long shopid
                              )
            throws ParseException {

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }


        Optional<Shop> shop = shopService.findById(shopid);
        model.addAttribute("shop", shop.get());

        List<Feat> shopFeats = shop.get().getShopFeats();
        model.addAttribute("feats", shopFeats);


        //블로그 검색 결과 api
        String clientId = "ettTXRX8Inpm4X2W5jlB";
        String clientSecret = "sB6ulOq5Z1";

        String text = null;
        try {
            text = URLEncoder.encode(shop.get().getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
        model.addAttribute("review", jsonObject);

        return "shop/hotel_detail";

    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }


}

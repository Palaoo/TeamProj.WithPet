package com.project.withpet.controller;

import com.project.withpet.domain.Feat;
import com.project.withpet.domain.Hotelroom;
import com.project.withpet.domain.Shop;
import com.project.withpet.service.HotelroomService;
import com.project.withpet.service.ShopService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.el.util.MessageFactory.get;

@Controller
public class ShopController {

    private final ShopService shopService;

    private final HotelroomService hotelroomService;

    @Autowired
    public ShopController(ShopService shopService, HotelroomService hotelroomService) {
        this.shopService = shopService;
        this.hotelroomService = hotelroomService;
    }

    @GetMapping("/hotel")
    public String hotelList(Model model, HttpServletRequest req){

        List<Shop> shops = shopService.shopList();
        model.addAttribute("shops", shops);

        shops.get(1).getShopFeats().get(1).getFeatname();

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        return "shop/hotel";
    }

    @GetMapping("/hotel/detail")
    public String hotelDetail(Model model, HttpServletRequest req,
                              @RequestParam("shopid") Long shopid) throws ParseException {

        HttpSession session = req.getSession();
        if(session.getAttribute("userid")!=null){
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        Optional<Shop> shop = shopService.findById(shopid);
        model.addAttribute("shop", shop.get());

        List<Feat> shopFeats = shop.get().getShopFeats();
        model.addAttribute("feats", shopFeats);

        List<Hotelroom> hotelrooms = hotelroomService.findByShopid(shopid);
        model.addAttribute("hotelrooms", hotelrooms);


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

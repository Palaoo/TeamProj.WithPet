package com.project.withpet.controller;

import com.project.withpet.domain.Hotelimg;
import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shopreview;
import com.project.withpet.dto.CafeDTOList;
import com.project.withpet.repository.HotelimgRepository;
import com.project.withpet.repository.cafeRepository;
import com.project.withpet.repository.shopreviewRepository;
import com.project.withpet.service.ShopLikeService;
import com.project.withpet.service.cafeService;
import com.project.withpet.service.reviewService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Controller
@Slf4j
public class cafeController {

    @Autowired
    private cafeService cafeService;

    @Autowired
    private cafeRepository cafeRepository;

    @Autowired
    private reviewService reviewService;

    @Autowired
    private shopreviewRepository shopreviewRepository;

    private final ShopLikeService shopLikeService;
    private final HotelimgRepository hotelimgRepository;

    public cafeController(ShopLikeService shopLikeService, HotelimgRepository hotelimgRepository) {
        this.shopLikeService = shopLikeService;
        this.hotelimgRepository = hotelimgRepository;
    }


    @GetMapping("cafe_list")   //카페 목록 가져오기
    public String viewList(Model model, HttpServletRequest req,
                           @PageableDefault(sort = "shopid", direction = Sort.Direction.DESC,size = 6)
                           Pageable pageable) {
        
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid",userid);


        List<cafe> cafeList = cafeService.findByshoptype(2L);
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {

            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(cafe.getShopid());
            String path = "";
            if (hotelimg.isPresent()) {
                path = hotelimg.get().getPath();
            } else {
                path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
            }

            //리뷰점수 평균
            Double avgByShopid = shopreviewRepository.getAvgByShopid(cafe.getShopid());
            if(avgByShopid==null){
                avgByShopid=0D;
            }

//            cafeDTOLists.add(new CafeDTOList(cafe, shopLike, path));
            //좋아요
            boolean shopLike = shopLikeService.islike(cafe.getShopid(), userid);
            Long likeCount = shopLikeService.getLikeCount(cafe.getShopid());
            cafeDTOLists.add(new com.project.withpet.dto.CafeDTOList(cafe, shopLike, path, likeCount, avgByShopid));

        }
        model.addAttribute("cafeDTOLists", cafeDTOLists);

        //페이징
        Page<cafe> cafes = cafeService.findCafes(pageable,2L);
        System.out.println(cafes.getTotalPages());
        int pageN = pageable.getPageNumber();
        int startPage = ((int) Math.floor(pageN / 5)) * 5+1;
        int totalPage = cafes.getTotalPages();
        int endpage = 0;
        if(totalPage < startPage + 4) {
            endpage = totalPage;
        } else {
            endpage = startPage + 4;
        }

        model.addAttribute("posts", cafes);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", cafes.hasNext());
        model.addAttribute("hasPrev", cafes.hasPrevious());
        model.addAttribute("totalPage", cafes.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endpage);

        return "cafe_list";
    }


    @GetMapping("/cafe_list/search")  //지역검색
    public String searchCafe(@RequestParam("keyword") String keyword, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        List<cafe> cafeList = cafeService.search(keyword, 2L);
        log.info("지역리스트 = " + cafeList.toString());
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {
            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(cafe.getShopid());
            String path = "";
            if (hotelimg.isPresent()) {
                path = hotelimg.get().getPath();
            } else {
                path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
            }
            boolean shopLike = shopLikeService.islike(cafe.getShopid(), userid);
            Long likeCount = shopLikeService.getLikeCount(cafe.getShopid());
            Double avgByShopid = shopreviewRepository.getAvgByShopid(cafe.getShopid());
            cafeDTOLists.add(new CafeDTOList(cafe, shopLike, path, likeCount, avgByShopid));
        }

        model.addAttribute("cafeDTOLists", cafeDTOLists);


        return "cafe_list";
    }


    @GetMapping("cafeinfo") //카페 상세정보
    public String showInfo(Model model, HttpServletRequest req, @RequestParam("shopid") Long shopid) throws ParseException {
        log.info("id= " + shopid);
        Optional<cafe> cafe = cafeService.findById(shopid);
        List<com.project.withpet.dto.CafeDTOList> cafeDTOLists = new ArrayList<>();
        cafe cafeinfo = cafe.get();
        model.addAttribute("cafe", cafeinfo);
        log.info(cafe.toString());

        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        //리뷰 리스트
        List<shopreview> shopreviewList = reviewService.findByshopid(shopid);
        model.addAttribute("shopreview", shopreviewList);

        //별점 평균
        float scoreTotal = 0;
        float scoreAvg = 0;
        if (!shopreviewList.isEmpty()) {
            for (shopreview shopreview : shopreviewList) {
                scoreTotal += shopreview.getScore();
                System.out.println("scoreTotal = " + scoreTotal);
            }
        }
        scoreAvg = scoreTotal / shopreviewList.size();
        model.addAttribute("scoreAvg", scoreAvg);
        log.info(shopreviewList.toString());

        //좋아요
        boolean shopLike = shopLikeService.islike(shopid, userid);
        model.addAttribute("shopLike", shopLike);
        Long likeCount = shopLikeService.getLikeCount(shopid);
        model.addAttribute("likecount", likeCount);

        Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopid);
        String path = hotelimg.get().getPath();
        model.addAttribute("shopimg", path);

        cafeDTOLists.add(new com.project.withpet.dto.CafeDTOList(cafe, shopLike, path, likeCount));

        //블로그 검색 결과 api
        String clientId = "ettTXRX8Inpm4X2W5jlB";
        String clientSecret = "sB6ulOq5Z1";

        String text = null;
        try {
            text = URLEncoder.encode(cafeinfo.getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
        model.addAttribute("review", jsonObject);


        return "cafeinfo";

    }


    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
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

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body) {
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

    @GetMapping("Restaurant-list")  //맛집 리스트
    public String viewRestList(Model model, HttpServletRequest req,
                               @PageableDefault(sort = "shopid", direction = Sort.Direction.DESC,size = 6)
                               Pageable pageable) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        List<cafe> cafeList = cafeService.findByshoptype(3L);
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {

            boolean shopLike = shopLikeService.islike(cafe.getShopid(), userid);
            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(cafe.getShopid());
            String path = "";
            if (hotelimg.isPresent()) {
                path = hotelimg.get().getPath();
            } else {
                path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
            }
            Long likeCount = shopLikeService.getLikeCount(cafe.getShopid());
            Double avgByShopid = shopreviewRepository.getAvgByShopid(cafe.getShopid());
            if(avgByShopid==null){
                avgByShopid=0D;
            }

            cafeDTOLists.add(new CafeDTOList(cafe, shopLike, path, likeCount, avgByShopid));
        }
        model.addAttribute("cafeList", cafeDTOLists);
        //페이징
        //페이징
        Page<cafe> cafes = cafeService.findCafes(pageable,3L);
        System.out.println(cafes.getTotalPages());
        int pageN = pageable.getPageNumber();
        int startPage = ((int) Math.floor(pageN / 5)) * 5+1;
        int totalPage = cafes.getTotalPages();
        int endpage = 0;
        if(totalPage < startPage + 4) {
            endpage = totalPage;
        } else {
            endpage = startPage + 4;
        }

        model.addAttribute("posts", cafes);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", cafes.hasNext());
        model.addAttribute("hasPrev", cafes.hasPrevious());
        model.addAttribute("totalPage", cafes.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endpage);

        return "Restaurant-list";
    }

    @GetMapping("/Restaurant-list/search")  //지역검색
    public String searchRestaurant(@RequestParam("keyword") String keyword, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        List<cafe> cafeList = cafeService.search(keyword, 3L);
        log.info("지역리스트 = " + cafeList.toString());
        List<CafeDTOList> cafeDTOLists = new ArrayList<>();
        for (cafe cafe : cafeList) {

            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(cafe.getShopid());
            String path = "";
            if (hotelimg.isPresent()) {
                path = hotelimg.get().getPath();
            } else {
                path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
            }
            boolean shopLike = shopLikeService.islike(cafe.getShopid(), userid);
            Long likeCount = shopLikeService.getLikeCount(cafe.getShopid());
            double avgByShopid = shopreviewRepository.getAvgByShopid(cafe.getShopid());
            cafeDTOLists.add(new CafeDTOList(cafe, shopLike, path, likeCount, avgByShopid));
        }

        model.addAttribute("cafeList", cafeDTOLists);
        log.info("맛집 좋아요 = " + cafeDTOLists.toString());

        return "Restaurant-list";
    }

    @GetMapping("restaurant-info") //맛집 상세정보
    public String showRestaurantInfo(Model model, HttpServletRequest req,
                                     @RequestParam("shopid") Long shopid) {
        log.info("id= " + shopid);
        Optional<cafe> cafe = cafeService.findById(shopid);
        cafe cafeinfo = cafe.get();
        model.addAttribute("cafe", cafeinfo);
        log.info(cafe.toString());

        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        model.addAttribute("userid", userid);

        //리뷰 리스트
        List<shopreview> shopreviewList = reviewService.findByshopid(shopid);
        model.addAttribute("shopreview", shopreviewList);
        log.info(shopreviewList.toString());

        //별점 평균
        float scoreTotal = 0;
        float scoreAvg = 0;
        if (!shopreviewList.isEmpty()) {

            for (shopreview shopreview : shopreviewList) {
                scoreTotal += shopreview.getScore();
                System.out.println("scoreTotal = " + scoreTotal);
            }
            scoreAvg = scoreTotal / shopreviewList.size();
            model.addAttribute("scoreAvg", scoreAvg);

            Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopid);
            String path = hotelimg.get().getPath();
            model.addAttribute("shopimg", path);

//            Long likeCount = shopLikeService.getLikeCount(shopid);
//            model.addAttribute("likecount", likeCount);

        }
        scoreAvg = scoreTotal / shopreviewList.size();
        model.addAttribute("scoreAvg", scoreAvg);
        log.info(shopreviewList.toString());


        //좋아요
        boolean shopLike = shopLikeService.islike(shopid, userid);
        model.addAttribute("shopLike", shopLike);
        Long likeCount = shopLikeService.getLikeCount(shopid);
        model.addAttribute("likecount", likeCount);

        Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopid);
        String path = hotelimg.get().getPath();
        model.addAttribute("shopimg", path);

        return "restaurant-info";
    }

//    @GetMapping("/{category_name}")
//    public String readAll(@PathVariable(required = false) String category_name,
//                          @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
//                          Pageable pageable, Model model){
//        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
//        page<CafeDTOList.
//    }

}
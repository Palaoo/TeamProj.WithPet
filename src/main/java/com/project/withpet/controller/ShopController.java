package com.project.withpet.controller;

import com.project.withpet.dto.HotelForm;
import com.project.withpet.dto.HotelroomForm;
import com.project.withpet.domain.*;
import com.project.withpet.dto.reviewDto;
import com.project.withpet.repository.*;
import com.project.withpet.repository.Booking.BookingRepository;
import com.project.withpet.repository.Hotelroom.HotelroomRepository;
import com.project.withpet.repository.Shop.ShopQueryRepository;
import com.project.withpet.repository.Shop.ShopRepository;
import com.project.withpet.service.HotelroomService;
import com.project.withpet.service.LikeHotelService;
import com.project.withpet.service.ShopService;
import com.project.withpet.service.UserService;
import com.project.withpet.service.reviewService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@Slf4j
public class ShopController {

    private final ShopService shopService;
    private final HotelroomService hotelroomService;
    private final ShopQueryRepository shopQueryRepository;

    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final HotelimgRepository hotelimgRepository;
    private final HotelroomimgRepository hotelroomimgRepository;
    private final reviewService reviewService;
    private final shopreviewRepository shopreviewRepository;
    private final LikeHotelService likeHotelService;
    private final FeatlistRepository featlistRepository;

    private final S3Uploader s3Uploader;
    private final ShopTypeRepository shopTypeRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public ShopController(ShopService shopService, HotelroomService hotelroomService, ShopQueryRepository shopQueryRepository, ShopRepository shopRepository, HotelroomRepository hotelroomRepository, UserService userService, BookingRepository bookingRepository, HotelimgRepository hotelimgRepository, HotelroomimgRepository hotelroomimgRepository, com.project.withpet.service.reviewService reviewService, com.project.withpet.repository.shopreviewRepository shopreviewRepository, LikeHotelService likeHotelService, FeatlistRepository featlistRepository, S3Uploader s3Uploader, ShopTypeRepository shopTypeRepository, RegionRepository regionRepository) {
        this.shopService = shopService;
        this.hotelroomService = hotelroomService;
        this.shopQueryRepository = shopQueryRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.hotelimgRepository = hotelimgRepository;
        this.hotelroomimgRepository = hotelroomimgRepository;
        this.reviewService = reviewService;
        this.shopreviewRepository = shopreviewRepository;
        this.likeHotelService = likeHotelService;
        this.featlistRepository = featlistRepository;
        this.s3Uploader = s3Uploader;
        this.shopTypeRepository = shopTypeRepository;
        this.regionRepository = regionRepository;
    }

    @GetMapping("/hotel")
    public String hotelList(Model model, HttpServletRequest req) {

        HttpSession session = req.getSession();
        String userId = (String) req.getSession().getAttribute("userLogined");
        model.addAttribute("userid", userId);

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

        if (week == 0 || week == 1 || week == 2 || week == 3 || week == 4 || week == 5) {
            switch (week) {
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

        List<Shop> availShop = shopQueryRepository.findAvailHotel(checkin, checkout, 2L);
        List<Shop> hotelList = shopService.hotelList(1L);


        List<HotelForm> hotelForms = new ArrayList<>();


        for (int i = 0; i < hotelList.size(); i++) {
            int liked = likeHotelService.isLiked(hotelList.get(i).getShopid(), userId);
            Long likeCount = likeHotelService.getLikeCount(hotelList.get(i).getShopid());

            addHotelForm(availShop, hotelList, hotelForms, i, likeCount, liked);
        }
        model.addAttribute("hotelList", hotelForms);
        model.addAttribute("person", 2);

        session.setAttribute("person", 2);
        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout", checkout);

        return "shop/hotel";
    }

    @PostMapping("/hotel")
    public String hotelinfo(Model model,
                            HttpServletRequest req,
                            @RequestParam("person") Long person,
                            @RequestParam("checkin") String checkin,
                            @RequestParam("checkout") String checkout,
                            @RequestParam("order") String order,
                            @RequestParam("size") Long size) {

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("person", person);
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        session.setAttribute("person", person);
        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout", checkout);

        List<Shop> availShop = shopQueryRepository.findAvailHotel(checkin, checkout, person);
        List<Shop> hotelList = shopService.hotelList(1L);

        List<HotelForm> hotelForms = new ArrayList<>();

        String userId = req.getSession().getAttribute("userid").toString();

        for (int i = 0; i < hotelList.size(); i++) {
            List<Feat> shopFeats = hotelList.get(i).getShopFeats();
            int liked = likeHotelService.isLiked(hotelList.get(i).getShopid(), userId);
            Long likeCount = likeHotelService.getLikeCount(hotelList.get(i).getShopid());
            if (size == 4) {
                addHotelForm(availShop, hotelList, hotelForms, i, likeCount, liked);
            } else {
                for (int j = 0; j < shopFeats.size(); j++) {
                    Long featid = shopFeats.get(j).getFeatid();
                    if (featid == size) {
                        addHotelForm(availShop, hotelList, hotelForms, i, likeCount, liked);
                    }
                }
            }
        }

        if (order.equals("price")) {
            Collections.sort(hotelForms, (a, b) -> (int) (b.getPrice() - a.getPrice()));
        } else {
//            Collections.sort(hotelForms, (a, b) -> (int) (b.getPrice() - a.getPrice()));
        }

        model.addAttribute("hotelList", hotelForms);

        return "shop/hotel";
    }

    @GetMapping("/hotel/detail")
    public String hotelDetail(Model model, HttpServletRequest req,
                              @RequestParam("shopid") Long shopid
    )
            throws ParseException, org.json.simple.parser.ParseException {

        HttpSession session = req.getSession();
        String userId = (String) req.getSession().getAttribute("userLogined");
        model.addAttribute("userid", userId);

        Long person = Long.parseLong(session.getAttribute("person").toString());
        String checkin = session.getAttribute("checkin").toString();
        String checkout = session.getAttribute("checkout").toString();
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("person", person);

        List<shopreview> shopreviewList = reviewService.findByshopid(shopid);
        model.addAttribute("shopreview", shopreviewList);

        Optional<Shop> shop = shopService.findById(shopid);
        model.addAttribute("shop", shop.get());
        Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(shopid);
        model.addAttribute("shopimg", hotelimg.get().getPath());

        List<Feat> shopFeats = shop.get().getShopFeats();
        model.addAttribute("feats", shopFeats);

        List<Hotelroom> hotelrooms = hotelroomService.findByShopid(shopid);
        List<Hotelroom> availRooms = shopQueryRepository.findAvailRoom(checkin, checkout, person, shopid);

        List<HotelroomForm> hotelroomForms = new ArrayList<>();

        for (int i = 0; i < hotelrooms.size(); i++) {

            addHotelRoomForm(hotelrooms, availRooms, hotelroomForms, i);
        }

        model.addAttribute("hotelrooms", hotelroomForms);

        int liked = likeHotelService.isLiked(shopid, userId);
        Long likeCount = likeHotelService.getLikeCount(shopid);
        model.addAttribute("liked", liked);
        model.addAttribute("likeCount", likeCount);


        //블로그 검색 결과 api
        String clientId = "ettTXRX8Inpm4X2W5jlB";
        String clientSecret = "sB6ulOq5Z1";

        String text = null;
        try {
            text = URLEncoder.encode(shop.get().getName(), "UTF-8");
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

        return "shop/hotel_detail";

    }

    @PostMapping("/reviews/createhotel")  //리뷰 등록
    public String createReview(reviewDto dto, HttpServletRequest req, @RequestParam("shopid") Long shopid, Model model) {
        HttpSession session = req.getSession();
        String userid = (String) session.getAttribute("userLogined");
        if (userid != null) {
            dto.setUserid(userid);
        }

        shopreview shopreview = dto.toEntity();

        shopreview saved = shopreviewRepository.save(shopreview);
        return "redirect:/hotel/detail?shopid=" + saved.getShopid();
    }

    @GetMapping("/reviews/deletehotel") //리뷰 삭제
    public String delete(reviewDto dto, @RequestParam("rid") Long rid, Long shopid) {
        reviewService.deleteReview(rid);
        shopreview shopreview = dto.toEntity();
        return "redirect:/hotel/detail?shopid=" + dto.getShopid();
    }

    @PostMapping("/reviews/updatehotel")  //리뷰 수정
    public String update(reviewDto dto) {
        shopreview shopreview = dto.toEntity();
        System.out.println(shopreview.getRid());
        shopreview target = shopreviewRepository.findById(shopreview.getRid()).orElse(null);
        if (target != null) {
            shopreviewRepository.save(shopreview);
        }
        return "redirect:/hotel/detail?shopid=" + shopreview.getShopid();
    }

    @PostMapping("/hotel/detail")
    @ResponseBody
    public List<HotelroomForm> hotelDetail(Model model,
                                           HttpServletRequest req,
                                           @RequestParam("shopid") Long shopid,
                                           @RequestParam("checkin") String checkin,
                                           @RequestParam("checkout") String checkout,
                                           @RequestParam("person") Long person) {

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        List<Hotelroom> hotelrooms = hotelroomService.findByShopid(shopid);
        List<Hotelroom> availRooms = shopQueryRepository.findAvailRoom(checkin, checkout, person, shopid);

        List<HotelroomForm> hotelroomForms = new ArrayList<>();

        for (int i = 0; i < hotelrooms.size(); i++) {
            addHotelRoomForm(hotelrooms, availRooms, hotelroomForms, i);
        }

        return hotelroomForms;

    }

    @GetMapping("/hotel/booking")
    public String newbook(HttpServletRequest req,
                          @RequestParam("paymentKey") String paymentKey,
                          @RequestParam("orderId") String orderID,
                          @RequestParam("amount") Long amount,
                          @RequestParam("checkin") String checkin,
                          @RequestParam("checkout") String checkout,
                          @RequestParam("roomid") Long roomid,
                          @RequestParam("name") String name,
                          @RequestParam("detail") String detail,
                          @RequestParam("mobile") String mobile) {
        HttpSession session = req.getSession();
        Booking booking = new Booking();
        booking.setCheckin(checkin);
        booking.setCheckout(checkout);
        booking.setTotal(amount);
        booking.setUserid((String) session.getAttribute("userid"));
        booking.setDetail(detail);
        booking.setRoomid(roomid);
        booking.setName(name);
        booking.setMobile(mobile);
        bookingRepository.save(booking);

        return "redirect:/mypage/mypage_booking";

    }

    @PostMapping("/hotel/booking")
    public String reservation(HttpServletRequest req,
                              Model model,
                              @RequestParam("roomid") Long roomid,
                              @RequestParam("checkin") String checkin,
                              @RequestParam("checkout") String checkout) throws java.text.ParseException {

        HttpSession session = req.getSession();
        model.addAttribute("userid", session.getAttribute("userid"));
        String userid = (String) session.getAttribute("userid");
        Optional<User> user = userService.findById(userid);
        model.addAttribute("username", user.get().getName());
        model.addAttribute("usermobile", user.get().getMobile());

        Optional<Hotelroom> hotelroom = hotelroomService.findById(roomid);
        model.addAttribute("hotelroom", hotelroom.get());

        Optional<Hotelroomimg> hotelroomimg = hotelroomimgRepository.findByShopid(roomid);
        model.addAttribute("hotelroomimg", hotelroomimg.get().getPath());

        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);

        Date checkinDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkin);
        Date checkoutDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkout);
        Long diffSec = (checkoutDate.getTime() - checkinDate.getTime()) / 1000;
        Long diffDays = diffSec / (24 * 60 * 60);

        model.addAttribute("days", diffDays);

        Long total = hotelroom.get().getPrice() * diffDays;
        model.addAttribute("total", total);

        return "shop/hotel_detail_reservation";
    }

    @GetMapping("append_likehotel")
    @ResponseBody
    public String appendLikeHotel(@RequestParam Long shopId, HttpServletRequest req) {
        System.out.println(req.getSession().getAttribute("userid").toString());
        if (likeHotelService.appendLike(shopId, req.getSession().getAttribute("userid").toString())) {
            return "1";
        } else {
            return "0";
        }
    }


    private void addHotelForm(List<Shop> availShop, List<Shop> hotelList, List<HotelForm> hotelForms, int i,
                              Long likeCount, int liked) {
        HotelForm hotelForm = new HotelForm();
        hotelForm.setShopid(hotelList.get(i).getShopid());
        hotelForm.setName(hotelList.get(i).getName());
        hotelForm.setIntro(hotelList.get(i).getIntro());
        hotelForm.setHour(hotelList.get(i).getHour());
        hotelForm.setInfo(hotelList.get(i).getInfo());
        hotelForm.setAddress(hotelList.get(i).getAddress());
        hotelForm.setTel(hotelList.get(i).getTel());
        hotelForm.setRegion(hotelList.get(i).getRegion());
        hotelForm.setShoptype(hotelList.get(i).getShoptype());
        Long shopid = hotelList.get(i).getShopid();
        hotelForm.setShopFeats(hotelList.get(i).getShopFeats());
        Optional<Hotelroom> cheapRoom = hotelroomService.cheapRoom(shopid);
        hotelForm.setPrice(cheapRoom.get().getPrice());
        hotelForm.setLikeCount(likeCount);
        hotelForm.setIsLiked(liked);
        for (int k = 0; k < availShop.size(); k++) {
            if (hotelList.get(i).getShopid() == availShop.get(k).getShopid()) {
                hotelForm.setAvail("true");
                break;
            } else {
                hotelForm.setAvail("false");
            }
        }
        Optional<Hotelimg> hotelimg = hotelimgRepository.findByShopid(hotelList.get(i).getShopid());
        String path = "";
        if (hotelimg.isPresent()) {
            path = hotelimg.get().getPath();
        } else {
            path = "https://withpetimg.s3.ap-northeast-2.amazonaws.com/images/hoteldefault.jpg";
        }
        hotelForm.setPath(path);
        hotelForms.add(hotelForm);
    }

    private void addHotelRoomForm(List<Hotelroom> hotelrooms, List<Hotelroom> availRooms, List<HotelroomForm> hotelroomForms, int i) {
        HotelroomForm hotelroomForm = new HotelroomForm();
        hotelroomForm.setRoomid(hotelrooms.get(i).getRoomid());
        hotelroomForm.setShopid(hotelrooms.get(i).getShopid());
        hotelroomForm.setRoomname(hotelrooms.get(i).getRoomname());
        hotelroomForm.setPrice(hotelrooms.get(i).getPrice());
        hotelroomForm.setPerson(hotelrooms.get(i).getPerson());
        hotelroomForm.setContent(hotelrooms.get(i).getContent());
        for (int j = 0; j < availRooms.size(); j++) {
            if (hotelrooms.get(i).getRoomid() == availRooms.get(j).getRoomid()) {
                hotelroomForm.setAvail("true");
                break;
            } else {
                hotelroomForm.setAvail("false");
            }
        }
        Optional<Hotelroomimg> hotelroomImg = hotelroomimgRepository.findByShopid(hotelrooms.get(i).getRoomid());
        hotelroomForm.setPath(hotelroomImg.get().getPath());
        hotelroomForms.add(hotelroomForm);
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

    @GetMapping("/hotel/search")  //지역검색
    public String searchHotel(@RequestParam("keyword") String keyword, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") == null) {
            return "login";
        }
        String userId = req.getSession().getAttribute("userid").toString();
        model.addAttribute("userid", userId);

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

        if (week == 0 || week == 1 || week == 2 || week == 3 || week == 4 || week == 5) {
            switch (week) {
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

        List<Shop> availShop = shopQueryRepository.findAvailHotel(checkin, checkout, 2L);

        List<Shop> hotelList = shopService.search(keyword, 1L);
        log.info("지역리스트 = " + shopService.toString());
        List<HotelForm> hotelForms = new ArrayList<>();


        for (int i = 0; i < hotelList.size(); i++) {
            int liked = likeHotelService.isLiked(hotelList.get(i).getShopid(), userId);
            Long likeCount = likeHotelService.getLikeCount(hotelList.get(i).getShopid());

            addHotelForm(availShop, hotelList, hotelForms, i, likeCount, liked);
        }
        model.addAttribute("hotelList", hotelForms);
        model.addAttribute("hotelList", hotelForms);
        model.addAttribute("person", 2);

        session.setAttribute("person", 2);
        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout", checkout);

        return "shop/hotel";
    }

    @PostMapping("/newShop")
    public String newProd(Shop shop, @RequestParam Long typeid, @RequestParam String featidList,
                          @RequestParam MultipartFile thumb,
                          @RequestParam(required = false) String[] roomname, @RequestParam(required = false) Long[] person,
                          @RequestParam(required = false) Long[] price, @RequestParam(required = false) String[] content,
                          @RequestParam(required = false) MultipartFile[] roomThumb,
                          HttpServletRequest req) throws IOException {

        shop.setBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));

        shop.setShoptype(shopTypeRepository.findById(typeid).get());

        String[] addressSplit = shop.getAddress().split(" ");
//        System.out.println("주소 = " + shop.getAddress());
//        System.out.println("addressSplit.length = " + addressSplit.length);
//        System.out.println("addressSplit[0] = " + addressSplit[0]);
        if (addressSplit[0].equals("세종특별자치시")) {
            addressSplit[0] = "세종";
        } else if (addressSplit[0].equals("제주특별자치도")) {
            addressSplit[0] = "제주도";
        }

        shop.setRegion(regionRepository.findByRegname(addressSplit[0]));

        Long shopid = shopService.save(shop);
        String[] featid = featidList.split(",");
//        System.out.println(featidList);
        for (int i = 0; i < featid.length; i++) {
//            System.out.println(featid[i]);
            featlistRepository.save(new Featlist(shopid, Long.parseLong(featid[i])));
        }

        String path = s3Uploader.uploadFiles(thumb, "thumbnail");

        hotelimgRepository.save(new Hotelimg(shopid, UUID.randomUUID().toString(), thumb.getOriginalFilename(), path));

        if (typeid == 1) {
            for (int i = 0; i < roomname.length; i++) {
                Hotelroom hotelroom = hotelroomService.save(new Hotelroom(shopid, roomname[i], price[i], person[i], content[i]));

                String roomPath = s3Uploader.uploadFiles(roomThumb[i], "thumbnail");
                hotelroomimgRepository.save(new Hotelroomimg(hotelroom.getRoomid(), UUID.randomUUID().toString(), roomThumb[i].getOriginalFilename(), roomPath));
            }

        }

        return "redirect:/businessInfo";
    }

}

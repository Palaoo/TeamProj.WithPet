package com.project.withpet.controller;

import com.project.withpet.domain.*;
import com.project.withpet.dto.HotelForm;
import com.project.withpet.repository.*;
import com.project.withpet.service.BusinessUserService;
import com.project.withpet.service.HotelroomService;
import com.project.withpet.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BusinessUserController {
    private final BusinessUserService businessUserService;
    private final Tools tools = new Tools();
    private final ShopService shopService;
    private final HotelimgRepository hotelimgRepository;

    private final FeatlistRepository featlistRepository;
    private final ShopTypeRepository shopTypeRepository;
    private final RegionRepository regionRepository;
    private final S3Uploader s3Uploader;
    private final HotelroomService hotelroomService;
    private final HotelroomimgRepository hotelroomimgRepository;

    @Autowired
    public BusinessUserController(BusinessUserService businessUserService, ShopService shopService, HotelimgRepository hotelimgRepository, FeatlistRepository featlistRepository, ShopTypeRepository shopTypeRepository, RegionRepository regionRepository, S3Uploader s3Uploader, HotelroomService hotelroomService, HotelroomimgRepository hotelroomimgRepository) {
        this.businessUserService = businessUserService;
        this.shopService = shopService;
        this.hotelimgRepository = hotelimgRepository;
        this.featlistRepository = featlistRepository;
        this.shopTypeRepository = shopTypeRepository;
        this.regionRepository = regionRepository;
        this.s3Uploader = s3Uploader;
        this.hotelroomService = hotelroomService;
        this.hotelroomimgRepository = hotelroomimgRepository;
    }

    @GetMapping("/businessPage")
    public String businessPage(HttpServletRequest req, Model model) {

        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }

        if (businessUserService.isBusinessUser(req.getSession().getAttribute("userid").toString()) == -1L) {
            return "registBusiness";
        }
        return "redirect:/businessInfo";
    }

    @GetMapping("/registBusiness")
    public String registBusiness(HttpServletRequest req) {
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 시작");
        businessUserService.join(req.getSession().getAttribute("userid").toString());
        System.out.println("From BUsinessUserController registBusiness(), 사업자 등록 완료");
        return "redirect:/businessInfo";
    }

    @GetMapping("/businessInfo")
    public String businessInfo(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }
        model.addAttribute("businessId", businessUserService.findByUid(req.getSession().getAttribute("userid").toString()).getBid());
        req.getSession().setAttribute("businessId", model.getAttribute("businessId").toString());
        return "businessInfo";
    }

    @GetMapping("/newShop")
    public String newShop(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }
        return "newShop";
    }

    @GetMapping("/shopInfo/updateShop")
    public String updateShop(@RequestParam Long shopid, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }

        Optional<Shop> shopOptional = shopService.findById(shopid);
        model.addAttribute("shop", shopOptional.get());

        if (shopOptional.get().getShoptype().getTypeid() == 1) {
            List<Hotelroom> hotelrooms = hotelroomService.findByShopid(shopid);
            model.addAttribute("hotelrooms", hotelrooms);
        }

        return "updateShop";
    }

    @PostMapping("/newShop")
    public String newShop(Shop shop, @RequestParam Long typeid, @RequestParam String featidList,
                          @RequestParam MultipartFile thumb,
                          @RequestParam(required = false) String[] roomname, @RequestParam(required = false) Long[] person,
                          @RequestParam(required = false) Long[] price, @RequestParam(required = false) String[] content,
                          @RequestParam(required = false) MultipartFile[] roomThumb,
                          HttpServletRequest req) throws IOException {

        shop.setBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));

        shop.setShoptype(shopTypeRepository.findById(typeid).get());

        String[] addressSplit = shop.getAddress().split(" ");
        if (addressSplit[0].equals("세종특별자치시")) {
            addressSplit[0] = "세종";
        } else if (addressSplit[0].equals("제주특별자치도")) {
            addressSplit[0] = "제주도";
        }

        shop.setRegion(regionRepository.findByRegname(addressSplit[0]));


        Long shopid = shopService.save(shop);
        String path = s3Uploader.uploadFiles(thumb, "thumbnail");
        hotelimgRepository.save(new Hotelimg(shopid, UUID.randomUUID().toString(), thumb.getOriginalFilename(), path));


        String[] featid = featidList.split(",");
        for (int i = 0; i < featid.length; i++) {

            featlistRepository.save(new Featlist(shopid, Long.parseLong(featid[i])));
        }

        if (typeid == 1) {
            for (int i = 0; i < roomname.length; i++) {
                Hotelroom hotelroom = hotelroomService.save(new Hotelroom(shopid, roomname[i], price[i], person[i], content[i]));
                String roomPath = s3Uploader.uploadFiles(roomThumb[i], "thumbnail");
                hotelroomimgRepository.save(new Hotelroomimg(hotelroom.getRoomid(), UUID.randomUUID().toString(), roomThumb[i].getOriginalFilename(), roomPath));
            }
        }
        return "redirect:/businessInfo";
    }

    @PostMapping("/updateShop")
    public String updateShop(Shop shop, @RequestParam Long typeid, @RequestParam String featidList,
                             @RequestParam(required = false) MultipartFile thumb, @RequestParam Long shopid,
                             @RequestParam(required = false) Long[] roomid,
                             @RequestParam(required = false) String[] roomname, @RequestParam(required = false) Long[] person,
                             @RequestParam(required = false) Long[] price, @RequestParam(required = false) String[] content,
                             @RequestParam(required = false) MultipartFile[] roomThumb,
                             HttpServletRequest req) throws IOException {

        shop.setBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));

        shop.setShoptype(shopTypeRepository.findById(typeid).get());

        String[] addressSplit = shop.getAddress().split(" ");
        if (addressSplit[0].equals("세종특별자치시")) {
            addressSplit[0] = "세종";
        } else if (addressSplit[0].equals("제주특별자치도")) {
            addressSplit[0] = "제주도";
        }

        shop.setRegion(regionRepository.findByRegname(addressSplit[0]));

        shop.setShopid(shopid);
        shopService.save(shop);
        if (!thumb.isEmpty()) {
            hotelimgRepository.deleteByShopid(shopid);
            String path = s3Uploader.uploadFiles(thumb, "thumbnail");
            hotelimgRepository.save(new Hotelimg(shopid, UUID.randomUUID().toString(), thumb.getOriginalFilename(), path));
        }


        String[] featid = featidList.split(",");
        for (int i = 0; i < featid.length; i++) {
            featlistRepository.deleteByShopid(shopid);
            featlistRepository.save(new Featlist(shopid, Long.parseLong(featid[i])));
        }

        if (typeid == 1) {
            for (int i = 0; i < roomname.length; i++) {
                Hotelroom hotelroom = hotelroomService.save(new Hotelroom(roomid[i], shopid, roomname[i], price[i], person[i], content[i]));
                if (!roomThumb[i].isEmpty()) {
                    hotelimgRepository.deleteByShopid(roomid[i]);
                    String roomPath = s3Uploader.uploadFiles(roomThumb[i], "thumbnail");
                    hotelroomimgRepository.save(new Hotelroomimg(hotelroom.getRoomid(), UUID.randomUUID().toString(), roomThumb[i].getOriginalFilename(), roomPath));
                }
            }
        }
        return "redirect:/businessInfo";
    }

    @GetMapping("/shopInfo")
    public String shopInfo(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("userid") != null) {
            model.addAttribute("userid", session.getAttribute("userid"));
        }

        if (!tools.isUserLogined(req)) {
            return "login";
        }
        List<Shop> shops = shopService.findAllByBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));
        System.out.println("shops size = " + shops.size());
        List<HotelForm> hotelForms = new ArrayList<>();
        for (Shop shop : shops) {
            Optional<Shop> shopOptional = shopService.findById(shop.getShopid());
            Optional<Hotelimg> hotelimgOptional = hotelimgRepository.findByShopid(shop.getShopid());
            hotelForms.add(new HotelForm(hotelimgOptional.get().getPath(), shopOptional.get()));
        }

        model.addAttribute("shopForms", hotelForms);

        return "shopInfo";
    }

    @GetMapping("/shopInfo/deleteShop")
    public String deleteShop(@RequestParam Long shopid, @RequestParam Long typeid) {
        shopService.deleteById(shopid);
        return "redirect:/shopInfo";
    }


    @GetMapping("appendProd")
    public String appendProd() {
        return "newProd";
    }



}

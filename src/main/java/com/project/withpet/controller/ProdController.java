package com.project.withpet.controller;

import com.project.withpet.domain.Cimg;
import com.project.withpet.domain.Img;
import com.project.withpet.domain.ProdReview;
import com.project.withpet.domain.Product;
import com.google.gson.JsonObject;
import com.project.withpet.dto.ProdDTO;
import com.project.withpet.service.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Controller
public class ProdController {
    private final S3Uploader s3Uploader;
    private final Tools tools = new Tools();
    private final ProdService prodService;
    private final ImgService imgService;
    private final CimgService cimgService;
    private final BusinessUserService businessUserService;
    private final LikeService likeService;
    private final ProdReviewService prodReviewService;

    @Autowired
    public ProdController(S3Uploader s3Uploader, ProdService prodService, ImgService imgService, CimgService cimgService, BusinessUserService businessUserService, LikeService likeService, ProdReviewService prodReviewService) {
        this.s3Uploader = s3Uploader;
        this.prodService = prodService;
        this.imgService = imgService;
        this.cimgService = cimgService;
        this.businessUserService = businessUserService;
        this.likeService = likeService;
        this.prodReviewService = prodReviewService;
    }


    @GetMapping("/mallPage")
    public String mallPage(HttpServletRequest req, Model model,
                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 9) Pageable pageable) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }
        model.addAttribute("userid", req.getSession().getAttribute("userid"));
        ArrayList<ProdDTO> pDTOs = new ArrayList<ProdDTO>();
        Page<Product> prods = prodService.findProds(pageable);
        System.out.printf("From prodController mallPage(), prods.getTotalElements(): %d\n", prods.getTotalElements());
        List<String> imgURLs = imgService.findImgURLs(prods);
        String userId = req.getSession().getAttribute("userid").toString();
        for (int i = 0; i < prods.getNumberOfElements(); i++) {
            Product product = prods.toList().get(i);
            String imgURL = imgURLs.get(i);
            String brand = businessUserService.findByBid(product.getBid()).getBrand();
            Long likeCount = likeService.getLikeCount(product.getId());

            ProdDTO pDTO = new ProdDTO(product, imgURL, brand, likeCount, likeService.isLiked(product.getId(), userId));
            pDTOs.add(pDTO);


        }
        int pageN = pageable.getPageNumber();
        int startPage = ((int) Math.floor(pageN / 5)) * 5 + 1;
        int totalPages = prods.getTotalPages();
        int endPage = 0;
        if (totalPages < startPage + 4) {
            endPage = totalPages;
        } else {
            endPage = startPage + 4;
        }


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", prods.getTotalPages());
        model.addAttribute("pDTOs", pDTOs);

//        model.addAttribute("img", imgService.findImgURLs(prods.getContent()));
        //        model.addAttribute("products", );
        return "mall";
    }

    @GetMapping("/userInfoPage")
    public String userInfo(HttpServletRequest req, Model model) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }

        return "userInfo";
    }

    @PostMapping("/newProd")
    public String newProd(@RequestParam String prodName, @RequestParam int price,
                          @RequestParam int type, HttpServletRequest req, @RequestParam MultipartFile thumb,
                          @RequestParam List<MultipartFile> cimgs, @RequestParam String content) throws IOException {

        content = content.replace("/summernoteImage/", "https://withpetimg.s3.ap-northeast-2.amazonaws.com/contents/");

        Product prod = new Product();
        prod.setName(prodName);
        prod.setDetail(content);
        prod.setPrice(price);
        prod.setType(type);
        prod.setBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));
        Long prodId = prodService.save(prod);
        System.out.printf("From ProdController newProd(), prodName:%s,  prodId:%d\n", prodName, prodId);

        String[] pathContent = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            pathContent[i] = s3Uploader.upload(files.get(i), "contents");
            Cimg cimg = new Cimg();
            cimg.setPath(pathContent[i]);
            cimg.setProdid(prodId);
            cimg.setOrigname("생략");
            cimg.setName(files.get(i).getName());
            cimgService.save(cimg);
        }

        files.clear();

        String pathThumb = s3Uploader.uploadFiles(thumb, "thumbnail");

        Img img = new Img();
//        for (MultipartFile file : files) {
        img.setOrigname(thumb.getOriginalFilename());
        img.setName(UUID.randomUUID().toString());  // 이부분 고쳐야함
        img.setProdid(prodId);
        img.setPath(pathThumb);
        imgService.save(img);
//        }

        System.out.println(pathThumb);


        return "redirect:/businessInfo";
    }

    ArrayList<File> files = new ArrayList<>();

    @PostMapping("/newprod/img")
    @ResponseBody
    public String uploadImg(HttpServletRequest req,
                            @RequestParam("file") MultipartFile multipartFile) {
        String fileRoot = "C:\\summernote_image\\";
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID() + extension;
        File targetFile = new File(fileRoot + savedFileName);
        JsonObject jsonObject = new JsonObject();
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImage/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");
            System.out.println("try=" + jsonObject.toString());
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
            System.out.println("catch=" + jsonObject.toString());
        }
        String a = jsonObject.toString();
        files.add(targetFile);
        return a;
    }

//    @RequestMapping(value = "/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
//    @ResponseBody
//    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
//                                            HttpServletRequest request) {
//        JSONObject jsonObject = new JSONObject();
//
//        /*
//         * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
//         */
//
//        // 내부경로로 저장
//        String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
//        String fileRoot = contextRoot + "resources/fileupload/";
//
//        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
//        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명
//
//        File targetFile = new File(fileRoot + savedFileName);
//        try {
//            InputStream fileStream = multipartFile.getInputStream();
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);    //파일 저장
//            jsonObject.addProperty("url", "/summernote/resources/fileupload/" + savedFileName); // contextroot + resources + 저장할 내부 폴더명
//            jsonObject.addProperty("responseCode", "success");
//
//        } catch (IOException e) {
//            FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
//            jsonObject.addProperty("responseCode", "error");
//            e.printStackTrace();
//        }
//        String a = jsonObject.toString();
//        return a;
//}


    @GetMapping("prod_view")
    public String prodView(Model model, HttpServletRequest req) {
        Long prodId = Long.parseLong(req.getParameter("prodId"));
        model.addAttribute("product", prodService.findById(prodId).get());   // 상품 튜플
        model.addAttribute("img", imgService.findByProdid(prodId).get().getPath());   // 썸네일 URL
        model.addAttribute("cimgs", cimgService.findImgURLs(prodId));
        List<ProdReview> pReviewList = prodReviewService.findByProdId(prodId);
        model.addAttribute("pReviewList", pReviewList);
        System.out.printf("ProdController prodView(), cimg url : %s", model.getAttribute("cimgs").toString());

        return "prod_view";
    }

//    @GetMapping("append_bascket")
//    public String appendBascket(@RequestParam Long prodId,HttpServletRequest req) {
//
//
//    }

    @GetMapping("append_like")
    @ResponseBody
    public String appendLike(@RequestParam Long prodId, HttpServletRequest req) {
        if (likeService.appendLike(prodId, req.getSession().getAttribute("userid").toString()))
            return "1";

        return "0";
    }

//    @GetMapping("delete_like")
//    @ResponseBody
//    public String deleteLike(@RequestParam Long prodId, HttpServletRequest req) {
//        likeService.deleteLike(prodId, req.getSession().getAttribute("userLogined").toString());
//        return "";
//    }


}

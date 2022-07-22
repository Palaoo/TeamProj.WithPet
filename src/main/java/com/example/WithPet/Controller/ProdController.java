package com.example.WithPet.Controller;

import com.example.WithPet.Service.CimgService;
import com.example.WithPet.Service.ImgService;
import com.example.WithPet.Service.ProdService;
import com.example.WithPet.domain.Cimg;
import com.example.WithPet.domain.Img;
import com.example.WithPet.domain.Product;
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
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Controller
public class ProdController {
    private final S3Uploader s3Uploader;
    private final Tools tools=new Tools();
    private final ProdService prodService;
    private final ImgService imgService;
    private final CimgService cimgService;

    @Autowired
    public ProdController(S3Uploader s3Uploader,  ProdService prodService, ImgService imgService, CimgService cimgService) {
        this.s3Uploader = s3Uploader;
        this.prodService = prodService;
        this.imgService = imgService;
        this.cimgService = cimgService;
    }


    @GetMapping("/mallPage")
    public String mallPage(HttpServletRequest req, Model model,
                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 6) Pageable pageable) {
        if (!tools.isUserLogined(req)) {
            return "login";
        }
        model.addAttribute("userLogined", req.getSession().getAttribute("userLogined"));
        ArrayList<ProdDTO> pDTOs = new ArrayList<ProdDTO>();
        Page<Product> prods = prodService.findProds(pageable);
        List<String> imgURLs = imgService.findImgURLs(prods);
        for (int i = 0; i < prods.getTotalElements(); i++) {
            Product product = prods.toList().get(i);
            String imgURL = imgURLs.get(i);
            ProdDTO pDTO = new ProdDTO(product, imgURL);
            pDTOs.add(pDTO);
        }
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
    public String newProd(@RequestParam String prodName, @RequestParam String detail, @RequestParam int price,
                          @RequestParam int type, HttpServletRequest req, @RequestParam MultipartFile thumb,
                          @RequestParam List<MultipartFile> cimgs) throws IOException {
//        modelMap.addAttribute("result", HttpStatus.SC_OK);
//
//        product.setBid(Long.parseLong(request.getSession().getAttribute("businessId").toString()));
//        System.out.printf("From ProdController newProd(), product .Bid: %d, Detail: %s, name: %s, price: %s, type:%s\n",
//                product.getBid(), product.getDetail(), product.getName(), product.getPrice(), product.getType());
//
//
//        prodService.save(product);


        String pathThumb = s3Uploader.uploadFiles(thumb, "thumbnail");
        System.out.println(pathThumb);

        Product prod = new Product();
        prod.setName(prodName);
        prod.setDetail(detail);
        prod.setPrice(price);
        prod.setType(type);
        prod.setBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));
        Long prodId = prodService.save(prod);
        System.out.printf("From ProdController newProd(), prodName:%s, setDetail:%s, prodId:%d\n", prodName, detail, prodId);

        Img img = new Img();
//        for (MultipartFile file : files) {
        img.setOrigname(thumb.getOriginalFilename());
        img.setName(UUID.randomUUID().toString());  // 이부분 고쳐야함
        img.setProdid(prodId);
        img.setPath(pathThumb);
        imgService.save(img);
//        }

        for (MultipartFile file : cimgs) {
            String pathContents = s3Uploader.uploadFiles(file, "contents");
            System.out.println(pathContents);
            Cimg cimg = new Cimg();
            cimg.setOrigname(file.getOriginalFilename());
            cimg.setName(UUID.randomUUID().toString());
            cimg.setProdid(prodId);
            cimg.setPath(pathContents);
            cimgService.save(cimg);
        }
        return "redirect:/businessInfo";
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
    public String prodView(Model model,HttpServletRequest req) {
        Long prodId = Long.parseLong(req.getParameter("prodId"));
        model.addAttribute("product", prodService.findById(prodId));   // 상품 튜플
        model.addAttribute("img", imgService.findByProdid(prodId).get().getPath());   // 썸네일 URL
        model.addAttribute("cimgs", cimgService.findImgURLs(prodId));



        return "prod_view";
    }


}

package com.example.WithPet.Controller;

import com.example.WithPet.Service.ImgService;
import com.example.WithPet.Service.ProdService;
import com.example.WithPet.domain.Img;
import com.example.WithPet.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class ProdController {
    private final S3Uploader s3Uploader;
    private final Tools tools = new Tools();
    private final ProdService prodService;
    private final ImgService imgService;

    public ProdController(S3Uploader s3Uploader, ProdService prodService, ImgService imgService) {
        this.s3Uploader = s3Uploader;
        this.prodService = prodService;
        this.imgService = imgService;
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
    public String newProd(@RequestParam String prodName, @RequestParam String detail,
                          @RequestParam int price, @RequestParam int type,
                          @RequestParam MultipartFile files, HttpServletRequest req) throws IOException {
        Product prod = new Product();
        prod.setName(prodName);
        prod.setDetail(detail);
        prod.setPrice(price);
        prod.setType(type);
        prod.setBid(Long.parseLong(req.getSession().getAttribute("businessId").toString()));
        Long prodId = prodService.save(prod);
        System.out.printf("From ProdController newProd(), prodName:%s, setDetail:%s, prodId:%d\n", prodName, detail, prodId);

        String path = s3Uploader.uploadFiles(files, "static");
        System.out.println(path);

        Img img = new Img();
//        for (MultipartFile file : files) {
        img.setOrigname(files.getOriginalFilename());
        img.setName(UUID.randomUUID().toString());
        img.setProdid(prodId);
        img.setPath(path);
        imgService.save(img);
//        }
        return "redirect:/businessInfo";
    }

}

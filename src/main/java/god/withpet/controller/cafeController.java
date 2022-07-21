package god.withpet.controller;

import god.withpet.entity.cafe;
import god.withpet.entity.region;
import god.withpet.repository.cafeRepository;
import god.withpet.service.cafeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class cafeController {

    @Autowired
    private cafeService cafeService;

    @Autowired
    private cafeRepository cafeRepository;


//    @GetMapping("cafe_list")
//    public String showCafe(Model model) {
//        List<cafe> cafeEntityList = cafeRepository.findAll();
//        model.addAttribute("cafeList", cafeEntityList);
//        log.info("From cafeController showCafe(), "+cafeEntityList.toString());
//        return "cafe_list";
//    }



    @GetMapping("cafe_list")
    public String viewList(Model model) {
        List<cafe> cafeList = cafeService.findAll();
        model.addAttribute("cafeList",cafeList);
        return "cafe_list";
    }

    @GetMapping("cafeinfo/{shopid}")
    public String showInfo(Model model, @RequestParam("shopid") Long shopid) {
        log.info("id= "+shopid);
        Optional<cafe> cafe = cafeService.findById(shopid);
        model.addAttribute("cafe",cafe);
        return "cafeinfo";
    }


}

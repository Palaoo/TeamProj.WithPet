package god.withpet.service;

import god.withpet.entity.shopreview;
import god.withpet.repository.cafeRepository;
import god.withpet.repository.shopreviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class reviewService {

    @Autowired
    private shopreviewRepository shopreviewRepository;

    @Autowired
    private cafeRepository cafeRepository;

//    public List<shopreview> showReview() {
//        return shopreviewRepository.findById(shopid);
//    }

    public List<shopreview> findAll() {
        return shopreviewRepository.findAll();
    }

//    public Optional<shopreview> findById(Long shopid) {
//        return shopreviewRepository.findById(shopid);
//    }


    public List<shopreview> findByshopid(Long shopid) {
        return shopreviewRepository.findByShopid(shopid);
    }

//    public Optional<shopreview> findById(Long shopid) {
//        return shopreviewRepository.findById(shopid);
//    }
}

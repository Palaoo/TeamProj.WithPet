package god.withpet.service;

import god.withpet.dto.reviewDto;
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

    public Optional<shopreview> findByUserId(String userid) {
        return shopreviewRepository.findByUserid(userid);
    }

    public Optional<shopreview> findById(Long rid) {
        return shopreviewRepository.findById(rid);
    }

    public List<shopreview> findAll() {
        return shopreviewRepository.findAll();
    }

    public List<shopreview> findByshopid(Long shopid) {
        return shopreviewRepository.findByShopid(shopid);
    }

    public void deleteReview(Long rid) {

        shopreviewRepository.deleteById(rid);
    }




//    public List<shopreview> findByuserid(String userid) {
//        return shopreviewRepository.findByUserid(userid);
//    }

}

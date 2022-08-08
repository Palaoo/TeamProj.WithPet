package com.project.withpet.service;

import com.project.withpet.domain.Shopreview;
import com.project.withpet.repository.cafeRepository;
import com.project.withpet.repository.shopreviewRepository;
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

//    public Optional<shopreview> findByUserid(String userid) {
//        return shopreviewRepository.findByUserid(userid);
//    }
        public List<Shopreview> findByuserid(String userid) {
            return shopreviewRepository.findByUserid(userid);
        }
//    public List<shopreview> findByUserId(String userid) {
//        return shopreviewRepository.findByUserid(userid);
//    }

    public Optional<Shopreview> findById(Long rid) {
        return shopreviewRepository.findById(rid);
    }

    public List<Shopreview> findAll() {
        return shopreviewRepository.findAll();
    }

    public List<Shopreview> findByshopid(Long shopid) {
        return shopreviewRepository.findByShopid(shopid);
    }

    public double getAvgByshopid(Long shopid) {
        return shopreviewRepository.getAvgByShopid(shopid);
    }

    public void deleteReview(Long rid) {

        shopreviewRepository.deleteById(rid);
    }




//    public List<shopreview> findByuserid(String userid) {
//        return shopreviewRepository.findByUserid(userid);
//    }

}

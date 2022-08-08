package com.project.withpet.service;

import com.project.withpet.dto.reviewDto;
import com.project.withpet.domain.cafe;
import com.project.withpet.domain.shopreview;
import com.project.withpet.repository.cafeRepository;
import com.project.withpet.repository.shopreviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class cafeService {

    @Autowired
    private cafeRepository cafeRepository;

    @Autowired
    private shopreviewRepository shopreviewRepository;



    public Optional<cafe> findById(Long shopid) {
        return cafeRepository.findById(shopid);
    }

    public List<cafe> findAll() {
        return cafeRepository.findAll();
    }

    public List<cafe> findByshoptype(Long typeid) {
        return cafeRepository.findAllByShoptypeTypeid(typeid);
    }

    public shopreview create(reviewDto dto) {
        shopreview shopreview = dto.toEntity();
        return shopreviewRepository.save(shopreview);
    }


    public List<cafe> search(String keyword, Long typeid) {
        return cafeRepository.findByAddressContainingAndShoptypeTypeid(keyword,typeid);
    }


}

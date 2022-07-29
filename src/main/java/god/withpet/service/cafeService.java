package god.withpet.service;

import god.withpet.dto.reviewDto;
import god.withpet.entity.cafe;
import god.withpet.entity.shopreview;
import god.withpet.repository.cafeRepository;
import god.withpet.repository.shopreviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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



}

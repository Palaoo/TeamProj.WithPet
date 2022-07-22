package god.withpet.service;

import god.withpet.entity.cafe;
import god.withpet.repository.cafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class cafeService {

    @Autowired
    private cafeRepository cafeRepository;

    public List<cafe> showCafe() {
        return cafeRepository.findAll();
    }

    public Optional<cafe> findById(Long shopid) {
        return cafeRepository.findById(shopid);
    }

    public List<cafe> findAll() {
        return cafeRepository.findAll();
    }


//    public Optional<cafe> showinfo(Long shopid) {
//        return cafeRepository.findById(shopid);
//    }



//    public List<featlist> showFeat() {
//        return cafeRepository.findById();
//    }
}

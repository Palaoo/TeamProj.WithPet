package com.example.WithPet.Service;

import com.example.WithPet.domain.Cimg;
import com.example.WithPet.domain.Img;
import com.example.WithPet.domain.Product;
import com.example.WithPet.repository.Cimg.CimgRepository;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CimgService {
    private final CimgRepository cimgRepository;

    public CimgService(CimgRepository cimgRepository) {
        this.cimgRepository = cimgRepository;
    }

    public Long save(Cimg cimg) {
        cimgRepository.save(cimg);
        return cimg.getId();
    }

    public List<Cimg> findCimgs() {
        return cimgRepository.findAll();
    }

    public Optional<Cimg> findOne(Long id) {
        return cimgRepository.findById(id);
    }

    public List<String> findImgURLs(Long prodid) {
//        ArrayList<String> pathList = new ArrayList<String>();
//        for (Product product : products) {
//            String path = cimgRepository.findByProdid(product.getId()).get().getPath();
//            System.out.printf("From CimgService, path = %s", path);
//            pathList.add(path);
//        }
//        return pathList;

        ArrayList<String> pathList = new ArrayList<>();
        for (Cimg cimg : cimgRepository.findByProdid(prodid)) {
            pathList.add(cimg.getPath());
        }
        return pathList;
    }
}

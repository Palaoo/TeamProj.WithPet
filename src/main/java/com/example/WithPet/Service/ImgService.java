package com.example.WithPet.Service;

import com.example.WithPet.domain.Img;
import com.example.WithPet.domain.Product;
import com.example.WithPet.repository.Img.ImgRepository;
import org.springframework.data.domain.Page;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImgService {
    private final ImgRepository imgRepository;

    public ImgService(ImgRepository imgRepository) {
        this.imgRepository = imgRepository;
    }

    public Long save(Img img) {
        imgRepository.save(img);
        return img.getId();
    }

    public List<Img> findImgs() {
        return imgRepository.findAll();
    }

    public Optional<Img> findOne(Long id) {
        return imgRepository.findById(id);
    }

    public Optional<Img> findByProdid(Long prodid) {
        return imgRepository.findByProdid(prodid);
    }

    public List<String> findImgURLs(Page<Product> products) {
        ArrayList<String> pathList = new ArrayList<String>();
        for (Product product : products) {
            String path = imgRepository.findByProdid(product.getId()).get().getPath();
            System.out.printf("From ImgService, path = %s\n", path);
            pathList.add(path);
        }
        return pathList;
    }
}

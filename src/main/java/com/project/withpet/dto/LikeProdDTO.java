package com.project.withpet.dto;

import com.project.withpet.domain.Like;
import com.project.withpet.domain.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LikeProdDTO {

    public Like like;
    public Product product;
    public String path;
}

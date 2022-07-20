package com.project.withpet.repository;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ShopRepositoryCustom {
    List<Tuple> findAll();
}

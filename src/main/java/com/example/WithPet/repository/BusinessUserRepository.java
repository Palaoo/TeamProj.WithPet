package com.example.WithPet.repository;

import com.example.WithPet.domain.BusinessUser;

import java.util.List;
import java.util.Optional;

public interface BusinessUserRepository {
    BusinessUser save(BusinessUser businessUser);

    Optional<BusinessUser> findByBusinessId(Long businessId);

    Optional<BusinessUser> findByUserId(String userid);

    List<BusinessUser> findAll();
}

package com.example.WithPet.repository.BusinessUser;

import com.example.WithPet.domain.BusinessUser;

import java.util.List;
import java.util.Optional;

public interface BusinessUserRepository {
    BusinessUser save(BusinessUser businessUser);

    Optional<BusinessUser> findById(Long bid);

    Optional<BusinessUser> findByUid(String uid);

    List<BusinessUser> findAll();
}

package com.example.WithPet.repository.BusinessUser;

import com.example.WithPet.domain.BusinessUser;

import java.util.List;
import java.util.Optional;

public interface BusinessUserRepository {
    BusinessUser save(BusinessUser businessUser);

    Optional<BusinessUser> findByBusiness_Id(Long business_id);

    Optional<BusinessUser> findByUser_Id(String user_id);

    List<BusinessUser> findAll();
}

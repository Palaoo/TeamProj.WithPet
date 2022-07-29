package com.project.withpet.repository.BusinessUser;

import com.project.withpet.domain.BusinessUser;

import java.util.List;
import java.util.Optional;

public interface BusinessUserRepository {
    BusinessUser save(BusinessUser businessUser);

    Optional<BusinessUser> findById(Long bid);

    Optional<BusinessUser> findByUid(String uid);

    List<BusinessUser> findAll();
}

package com.example.WithPet.Service;

import com.example.WithPet.domain.BusinessUser;
import com.example.WithPet.repository.BusinessUser.BusinessUserRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BusinessUserService {
    private final BusinessUserRepository businessUserRepository;

    public BusinessUserService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;
    }

    public Long join(BusinessUser businessUser) {
        validDuplicateBusinessUser(businessUser);

        businessUserRepository.save(businessUser);
        return businessUser.getBusiness_id();
    }

    public Long isBusinessUser(String user_id) {
        try {
            return businessUserRepository.findByUser_Id(user_id).get().getBusiness_id();
        } catch (IllegalStateException e) {
            return -1L;
        }

    }

    private void validDuplicateBusinessUser(BusinessUser businessUser) {
        businessUserRepository.findByUser_Id(businessUser.getUser_id())
                .ifPresent(b -> {
                    throw new IllegalStateException("이미 사업자 ID가 존재합니다.");
                });
    }
}

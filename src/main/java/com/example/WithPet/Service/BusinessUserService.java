package com.example.WithPet.Service;

import com.example.WithPet.domain.BusinessUser;
import com.example.WithPet.repository.BusinessUserRepository;
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
        return businessUser.getUserId();
    }

    public Long isBusinessUser(String userid) {
        try {
            return businessUserRepository.findByUserId(userid).get().getUserId();
        } catch (IllegalStateException e) {
            return -1L;
        }

    }

    private void validDuplicateBusinessUser(BusinessUser businessUser) {
        businessUserRepository.findByUserId(businessUser.getUser())
                .ifPresent(b -> {
                    throw new IllegalStateException("이미 사업자 ID가 존재합니다.");
                });
    }
}

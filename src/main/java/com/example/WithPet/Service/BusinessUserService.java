package com.example.WithPet.Service;

import com.example.WithPet.domain.BusinessUser;
import com.example.WithPet.repository.BusinessUser.BusinessUserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional
public class BusinessUserService {
    private final BusinessUserRepository businessUserRepository;

    public BusinessUserService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;
    }

    public Long join(String uid) {
        validDuplicateBusinessUser(uid);
        BusinessUser businessUser=new BusinessUser();
        businessUser.setUid(uid);

        businessUserRepository.save(businessUser);
        return businessUserRepository.findByUid(uid).get().getBid();
    }

    public Long isBusinessUser(String uid) {
        try {
            return businessUserRepository.findByUid(uid).get().getBid();
        } catch (IllegalStateException e) {
            return -1L;
        } catch (NoSuchElementException e) {
            return -1L;
        }

    }

    public BusinessUser findByUid(String uid) {
        return businessUserRepository.findByUid(uid).get();
    }

    private void validDuplicateBusinessUser(String uid) {
        businessUserRepository.findByUid(uid)
                .ifPresent(b -> {
                    throw new IllegalStateException("이미 사업자 ID가 존재합니다.");
                });
    }
}

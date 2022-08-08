package com.project.withpet.service;

import com.project.withpet.domain.BusinessUser;
import com.project.withpet.repository.BusinessUser.BusinessUserRepository;
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

    public BusinessUser findByBid(Long bid) {
        return businessUserRepository.findById(bid).get();
    }
<<<<<<< HEAD
=======


>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}

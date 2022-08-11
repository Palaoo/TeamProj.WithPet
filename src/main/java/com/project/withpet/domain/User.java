package com.project.withpet.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    String userid;
    String password;
    String name;
    String mobile;

    @Column(name = "kakao_email")
    String kakaoEmail;


    public String getKakaoEmail() {
        return kakaoEmail;
    }

    public void setKakaoEmail(String kakaoEmail) {
        this.kakaoEmail = kakaoEmail;
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}

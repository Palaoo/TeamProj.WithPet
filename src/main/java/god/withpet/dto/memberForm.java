package god.withpet.dto;

import god.withpet.entity.member;

public class memberForm {

    private String userid;
    private String password;
    private String name;
    private String mobile;

    public memberForm(String userid,String password,String name,String mobile) {
        this.userid= userid;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
    }

    public member toEntity() {
        return new member(userid,password,name,mobile);
    }
}

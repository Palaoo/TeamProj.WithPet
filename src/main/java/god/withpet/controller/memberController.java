//package god.withpet.controller;
//
//import god.withpet.dto.memberForm;
//import god.withpet.entity.member;
//import god.withpet.repository.memberRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.Mapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.time.LocalDate;
//
//
//@Slf4j
//@Controller
//public class memberController {
//
//    @Autowired
//    private memberRepository memberRepository;
//
//
//    @GetMapping("login")
//    public String doLogin(){
//
//        return "login";
//    }
//
//
//    @GetMapping("signup")
//    public String newmemberForm(){
//        return "signup";
//    }
//
//    @PostMapping("/signup/create")
//    public String createMember(memberForm form) {
//        member member = form.toEntity();
//
//        member saved = memberRepository.save(member);
//        return"signup";
//    }
//
//}



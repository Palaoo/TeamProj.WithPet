package com.project.withpet.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.withpet.domain.User;
import com.project.withpet.repository.User.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String userid) {
        return userRepository.findById(userid);
    }

    public Optional<User> findByKakaoEmail(String kakaoEamil) {
        return userRepository.findByKakaoEmail(kakaoEamil);
    }

    public void updateKakaoEmail(String userId, String kakaoEmail) {
        userRepository.updateKakaoEmail(userId, kakaoEmail);
    }

    public int kakaoUserValidation(String kakaoEmail) {
        Optional<User> result = userRepository.findByKakaoEmail(kakaoEmail);
        if (result.isPresent())
            return 1;

        if (result.isEmpty())
            if (userRepository.findById(kakaoEmail).isPresent())
                return 2;
            else
                return 0;

        return 0;
    }

    public boolean checkUser(String userId, String password) {
        Optional<User> user = userRepository.findById(userId);
        try {
            if (user.get().getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(User user) {
        Optional<User> findUser = userRepository.findById(user.getUserId());

        try {
            System.out.printf("From UserService.login(), %s\n", findUser.get().getUserId());
        } catch (NoSuchElementException e) {
            return false;
        }

        if (!findUser.get().getPassword().equals(user.getPassword())) {
            return false;
        }
        System.out.println("From UserService.login(), id, pwd 모두 일치");
        return true;
    }

    public String join_str(User user) {
        validDuplicateUser(user);

        userRepository.save(user);
        return user.getUserId();
    }

    public Optional<User> join(User user) {
        userRepository.save(user);
        return Optional.ofNullable(user);
    }

    ;

    public void validDuplicateUser(User user) {
        userRepository.findById(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        System.out.println("ID 중복 검사 통과");
    }

    public void delete(String id) {
        userRepository.deleteByUserid(id);
    }

    /*public Map<String, String> validateHandling(BindingResult errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }*/

    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id=ba550e3dbc4a3eca138a7aaddfa3e70a"); //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8080/kakao-users"); // 본인이 설정한 주소

            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public HashMap<String, Object> getUserInfo(String access_Token) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
//            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            userInfo.put("email", email);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }
}

package com.example.recaptchaservice.service;

import com.example.recaptchaservice.response.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {
    @Value("${recaptcha.v3.secret}")
    private String recaptchaV3Secret;
    @Value("${recaptcha.v2.secret}")
    private String recaptchaV2Secret;
    @Value("${recaptcha.severURL}")
    private String recaptchaSever;
    @Value("${recaptcha.v3.threshold}")
    private Float threshold;

    public boolean verifyV3(String recaptchaResponse, Float threshold, String action) {

        GoogleResponse googleResponse = sendRequest(recaptchaResponse, recaptchaV3Secret);
        if (googleResponse == null) {
            return false;
        }
        System.out.println(googleResponse);
        if (!googleResponse.getAction().equals(action)) {
            return false;
        }
        if (threshold == 0f) {
            return googleResponse.getScore() > this.threshold;
        }
        return googleResponse.getScore() > threshold;
    }

    public boolean verifyV2(String recaptchaResponse) {
        GoogleResponse googleResponse = sendRequest(recaptchaResponse, recaptchaV2Secret);
        System.out.println(googleResponse);
        if (googleResponse == null) {
            return false;
        }
        return googleResponse.isSuccess();
    }

    private GoogleResponse sendRequest(String recaptchaResponse, String recaptchaSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", recaptchaResponse);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(recaptchaSever, request, GoogleResponse.class);
    }


}

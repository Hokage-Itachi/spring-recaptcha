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
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;
    @Value("${recaptcha.severURL}")
    private String recaptchaSever;
    @Value("${recaptcha.threshold}")
    private Float threshold;

    public boolean verify(String recaptchaResponse, Float threshold, String action) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", recaptchaResponse);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        GoogleResponse googleResponse = restTemplate.postForObject(recaptchaSever, request, GoogleResponse.class);
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
}

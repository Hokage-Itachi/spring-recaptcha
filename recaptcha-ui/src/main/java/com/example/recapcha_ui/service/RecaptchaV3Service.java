package com.example.recapcha_ui.service;

import com.example.recapcha_ui.response.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaV3Service {
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;
    @Value("${recaptcha.severURL}")
    private String recaptchaSever;
    @Value("${recaptcha.threshold}")
    private Float threshold;

    public boolean verify(String recaptchaResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", recaptchaResponse);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        GoogleResponse googleResponse = restTemplate.postForObject(recaptchaSever, request, GoogleResponse.class);
        assert googleResponse != null;
        return googleResponse.getScore() > threshold;
    }
}

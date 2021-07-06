package com.example.recapcha_ui.controller;

import com.example.recapcha_ui.request.OTPLoginRequest;
import com.example.recapcha_ui.response.VerifyResponse;
import com.example.recapcha_ui.utils.ControllerUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {
    private String severURL = "http://localhost:8081/api";
    private RestTemplate restTemplate;

    public BaseController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String renderLogin(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String otpLogin() {
        return "otp";
    }

    @PostMapping("/verify")
    public String login(HttpServletRequest request) {
        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Recaptcha", recaptchaResponse);

        HttpEntity<Map<String, String>> verifyRequest = new HttpEntity<>(null, headers);
        try {
            String response = restTemplate.postForObject(severURL + "/form-verify", verifyRequest, String.class);
            System.out.println(response);

            return "redirect:/success";

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 500) {
                return "500";
            } else if (e.getStatusCode().value() == 400){
                return "400";
            }
            System.out.println(e.getMessage());
        }
        return "redirect:/otp-login";
    }
    @PostMapping("/register")
    public String register(HttpServletRequest request) {
        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Recaptcha", recaptchaResponse);

        HttpEntity<Map<String, String>> verifyRequest = new HttpEntity<>(null, headers);
        try {
            String response = restTemplate.postForObject(severURL + "/register", verifyRequest, String.class);
            System.out.println(response);

            return "redirect:/success";

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 500) {
                return "500";
            } else if (e.getStatusCode().value() == 400){
                return "400";
            }
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }


    @GetMapping("/success")
    public String success() {
        return "success";
    }

}

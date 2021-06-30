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
    private String severURL = "http://localhost:8081";
    private RestTemplate restTemplate;
    private Map<String, Integer> loginFailedCount;

    public BaseController() {
        this.loginFailedCount = new HashMap<>();
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

    @GetMapping("/otp-login")
    public String otpLogin(Model model, @RequestParam("email") String email) {
        model.addAttribute("otpLoginForm", new OTPLoginRequest(email, ""));
        return "otp";
    }

    @PostMapping("/verify")
    public String login(HttpServletRequest request) {
        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        String userEmail = request.getParameter("email");
        String password = request.getParameter("password");
        Map<String, String> data = ControllerUtils.createRequestData(userEmail, password, recaptchaResponse);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> verifyRequest = new HttpEntity<>(data, headers);
        try {
            VerifyResponse response = restTemplate.postForObject(severURL + "/form-verify", verifyRequest, VerifyResponse.class);
            System.out.println(response);
            if (response.getStatus().equals("200")) {
                return "redirect:/success";
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 500){
                return "500";
            }
            System.out.println(e.getMessage());
        }
        return "redirect:/otp-login?email=" + userEmail;
    }

    @PostMapping("/otp-verify")
    public String otpVerify(@ModelAttribute("otpLoginForm") OTPLoginRequest otpLoginRequest, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestData = new HashMap<>();
        requestData.put("email", otpLoginRequest.getEmail());
        requestData.put("otp", otpLoginRequest.getOtp());
        HttpEntity<Map<String, String>> otpVerifyRequest = new HttpEntity<>(requestData, headers);

        try {
            String response = restTemplate.postForObject(severURL + "/otp-verify", otpVerifyRequest, String.class);
        } catch (HttpStatusCodeException e) {
            System.out.println(e.getMessage());
            String email = otpLoginRequest.getEmail();
            Integer loginFailedNumber = loginFailedCount.get(email);
            if (loginFailedNumber == null) {
                loginFailedNumber = 1;
            }
            if (loginFailedNumber > 3) {
                String error = "Login failed more than 3 times. Please try again in a few minutes";
                return "redirect:/login?error=" + error;
            }
            loginFailedCount.put(email, loginFailedNumber + 1);
            return "redirect:/otp-login?email=" + otpLoginRequest.getEmail() + "&error=OTP invalid";
        }
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

}

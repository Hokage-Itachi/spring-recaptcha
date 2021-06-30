package com.example.recaptchaservice.controller;

import com.example.recaptchaservice.request.FormRequest;
import com.example.recaptchaservice.request.OTPVerifyRequest;
import com.example.recaptchaservice.response.VerifyResponse;
import com.example.recaptchaservice.service.MailService;
import com.example.recaptchaservice.service.RecaptchaService;
import com.example.recaptchaservice.utils.RecaptchaUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RecaptchaController {
    private final RecaptchaService recaptchaService;
    private final MailService mailService;
    private Map<String, String> pendingVerifyMails;

    public RecaptchaController(RecaptchaService recaptchaService, MailService mailService) {
        this.recaptchaService = recaptchaService;
        this.mailService = mailService;
        this.pendingVerifyMails = new HashMap<>();
    }

    @PostMapping("/form-verify")
    public ResponseEntity<Object> formVerify(@RequestBody FormRequest request) {
        String recaptchaResponse = request.getGoogleRecaptchaResponse();
        VerifyResponse verifyResponse = recaptchaService.verify(recaptchaResponse);
        if (verifyResponse.getStatus().equals("200")) {
            return new ResponseEntity<>(verifyResponse, HttpStatus.OK);
        }
        String email = request.getEmail();
        String otp = RecaptchaUtils.createOTP();
        pendingVerifyMails.put(email, otp);
        mailService.sendMail(email, otp);
        return new ResponseEntity<>(verifyResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/otp-verify")
    public ResponseEntity<Object> otpVerify(@RequestBody OTPVerifyRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();
        if (otp.equals(pendingVerifyMails.get(email))) {
            pendingVerifyMails.remove(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            String newOTP = RecaptchaUtils.createOTP();
            pendingVerifyMails.put(email, newOTP);
            mailService.sendMail(email, newOTP);
            return new ResponseEntity<>("OTP invalid", HttpStatus.BAD_REQUEST);
        }
    }
}

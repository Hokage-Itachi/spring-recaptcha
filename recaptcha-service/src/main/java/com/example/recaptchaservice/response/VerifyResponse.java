package com.example.recaptchaservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResponse {
    private String timestamp;
    private String status;
    private String message;
    private String [] errors;
}

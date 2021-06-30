package com.example.recaptchaservice.utils;

import com.example.recaptchaservice.response.VerifyResponse;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Random;

public class RecaptchaUtils {
    public static VerifyResponse createResponseData(String message, String[] error, String status) {
        VerifyResponse responseData = new VerifyResponse();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        responseData.setTimestamp(timestamp.toString());
        responseData.setStatus(status);
        responseData.setMessage(message);
        responseData.setErrors(error);

        return responseData;
    }

    public static String createOTP(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}

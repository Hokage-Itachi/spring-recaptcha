package com.example.recapcha_ui.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ControllerUtils {
    public static String generateOTP() {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        return otp;
    }

    public static Map<String, String> createRequestData(String... params) {
        Map<String, String> data = new HashMap<>();
        data.put("email", params[0]);
        data.put("password", params[1]);
        data.put("g-recaptcha-response", params[2]);
        return data;
    }
}

package com.example.recapcha_ui.utils;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {

    public static Map<String, String> createRequestData(String... params) {
        Map<String, String> data = new HashMap<>();
        data.put("email", params[0]);
        data.put("password", params[1]);
        data.put("g-recaptcha-response", params[2]);
        return data;
    }
}

package com.podlobby.podlobby.services;

import com.podlobby.podlobby.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class Recaptcha {

    @Value("${recaptcha.checkbox.secret.key}")
    private String recaptchaCheckboxSecret;

    @Value("${recaptcha.invisible.secret.key}")
    private String recaptchaInvisibleSecret;

    @Value("${recaptcha.testing.secret.key}")
    private String recaptchaTestingSecret;

    private static final String recaptchaServerURL = "https://www.google.com/recaptcha/api/siteverify";


}
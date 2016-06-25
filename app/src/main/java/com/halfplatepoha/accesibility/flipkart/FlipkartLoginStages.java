package com.halfplatepoha.accesibility.flipkart;

/**
 * Created by surajkumarsau on 06/06/16.
 */
public enum FlipkartLoginStages {
    ZERO("zero"),
    SPLASH_SCREEN("splash"),
    SIGNUP_SIGNIN_SCREEN("signup_signin"),
    SIGNUP_SIGNIN_SCREEN_EXISTING("signup_signin_existing"),
    SIGNUP_SCREEN("signup"),
    SIGNUP_SCREEN_BEFORE_MOBILE_CLICK("signup_mobile_click"),
    SIGNUP_SCREEN_AFTER_MOBILE_CLICK("signup_after_mobile_click"),
    AUTOVERIFICATION_SCREEN("autoverify"),
    POST_AUTOVERIFICATION_SCREEN("post_autoverify"),
    OTP_SCREEN("otp_screen");

    String value;

    FlipkartLoginStages(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

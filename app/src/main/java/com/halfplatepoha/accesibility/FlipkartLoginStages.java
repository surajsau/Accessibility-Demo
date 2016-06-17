package com.halfplatepoha.accesibility;

/**
 * Created by surajkumarsau on 06/06/16.
 */
public enum FlipkartLoginStages {
    ZERO("zero"),
    SPLASH_SCREEN("splash"),
    SIGNUP_SIGNIN_SCREEN("signup_signin"),
    SIGNUP_SCREEN("signup"),
    SIGNUP_SCREEN_MOBILE_CLICK("signup_mobile_click"),
    SINGUP_SCREEN_KEYBOARD("signup_keyboard"),
    AUTOVERIFICATION_SCREEN("autoverify"),
    OTP_SCREEN("otp_screen");

    String value;

    FlipkartLoginStages(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

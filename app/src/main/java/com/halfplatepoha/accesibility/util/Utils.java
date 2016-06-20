package com.halfplatepoha.accesibility.util;

import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;

/**
 * Created by surajkumarsau on 18/06/16.
 */
public class Utils {

    public static String getFirstChoice(FlipkartLoginStages stage) {
        switch (stage) {
            case SIGNUP_SIGNIN_SCREEN:
                return "Naye";
            case SIGNUP_SIGNIN_SCREEN_EXISTING:
                return "Email";
        }
        return "";
    }

    public static String getSecondChoice(FlipkartLoginStages stage) {
        switch (stage) {
            case SIGNUP_SIGNIN_SCREEN:
                return "Purane";
            case SIGNUP_SIGNIN_SCREEN_EXISTING:
                return "Phone Number";
        }
        return "";
    }
}

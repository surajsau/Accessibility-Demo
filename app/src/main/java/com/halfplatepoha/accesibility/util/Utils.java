package com.halfplatepoha.accesibility.util;

import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;

/**
 * Created by surajkumarsau on 18/06/16.
 */
public class Utils {

    public static String getFirstChoice(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return "Naye";
            case SIGNUP_SCREEN:
                return "Email";
        }
        return "";
    }

    public static String getSecondChoice(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return "Purane";
            case SIGNUP_SCREEN:
                return "Phone Number";
        }
        return "";
    }
}

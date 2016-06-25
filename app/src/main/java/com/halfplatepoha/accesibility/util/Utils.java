package com.halfplatepoha.accesibility.util;

import com.halfplatepoha.accesibility.ChoiceResults;
import com.halfplatepoha.accesibility.HindiHelpStrings;
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
            case OTP_SCREEN:
                return "Haan";
        }
        return "";
    }

    public static String getSecondChoice(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return "Purane";
            case SIGNUP_SCREEN:
                return "Phone Number";
            case OTP_SCREEN:
                return "Nahi";
        }
        return "";
    }

    public static String getFirstChoiceResult(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return ChoiceResults.NEW_USER;
            case OTP_SCREEN:
                return ChoiceResults.OTP_YES;
        }
        return "";
    }

    public static String getSecondChoiceResult(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return ChoiceResults.EXISTING_USER;
            case OTP_SCREEN:
                return ChoiceResults.OTP_NO;
        }
        return "";
    }

    public static String getSpeechString(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return HindiHelpStrings.NEW_OLD_USER;
            case SIGNUP_SIGNIN_SCREEN:
                return HindiHelpStrings.NEW_ACCOUNT_CREATE;
            case SIGNUP_SCREEN_BEFORE_MOBILE_CLICK:
                return HindiHelpStrings.NEW_ENTER_MOBILE_NUMBER;
            case SIGNUP_SCREEN_AFTER_MOBILE_CLICK:
                return HindiHelpStrings.NEW_CLICK_SIGNUP_AFTER_MOBILE_NUMBER;
            case AUTOVERIFICATION_SCREEN:
                return HindiHelpStrings.MOBILE_AUTH_SCREEN;
            case OTP_SCREEN:
                return HindiHelpStrings.AUTH_FAILURE_OTP_YES_NO;

        }
        return null;
    }
}

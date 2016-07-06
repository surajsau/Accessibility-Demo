package com.halfplatepoha.accesibility.util;

import com.halfplatepoha.accesibility.ChoiceResults;
import com.halfplatepoha.accesibility.HindiHelpStrings;
import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;

/**
 * Created by surajkumarsau on 18/06/16.
 */
public class Utils {

    /**
     * For the first option button
     * @param stage logical stage in the flow
     * @return string to be shown on popup
     */
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

    /**
     * For the second option button
     * @param stage logical stage in flow
     * @return string to be shown on popup
     */
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

    /**
     * @param stage
     * @return result to be sent back in Intent on clicking FirstChoice
     */
    public static String getFirstChoiceResult(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return ChoiceResults.NEW_USER;
            case OTP_SCREEN:
                return ChoiceResults.OTP_YES;
        }
        return "";
    }

    /**
     * @param stage
     * @return result to be sent back in Intent on clicking SecondChoice
     */
    public static String getSecondChoiceResult(FlipkartLoginStages stage) {
        switch (stage) {
            case ZERO:
                return ChoiceResults.EXISTING_USER;
            case OTP_SCREEN:
                return ChoiceResults.OTP_NO;
        }
        return "";
    }

    /**
     * @param stage
     * @return hindi string to be spoken by the TTS at a given stage
     */
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

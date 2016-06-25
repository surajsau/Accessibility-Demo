package com.halfplatepoha.accesibility.flipkart;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import com.halfplatepoha.accesibility.BaseHelper;
import com.halfplatepoha.accesibility.Finder;
import com.halfplatepoha.accesibility.ViewIdTextModel;

/**
 * Created by surajkumarsau on 06/06/16.
 */
public class FlipkartHelper extends BaseHelper {

    public FlipkartHelper(Finder finder, String baseId) {
        super(finder, baseId);
    }

    //--not working on all devices (different launchers issue)
    public boolean isFlipkartAppIconClicked(AccessibilityNodeInfoCompat root) {
        return (root.getContentDescription() != null ?
                "Flipkart".equalsIgnoreCase(root.getContentDescription().toString()):false);
    }

    public boolean isFlipkartHomeScreenReached(AccessibilityNodeInfoCompat root) {
        return (mBaseId.concat("drawer_layout").equalsIgnoreCase(root.getViewIdResourceName()));
    }

    public boolean isSignUpButtonClicked(AccessibilityNodeInfoCompat root) {
        return (mBaseId.concat("btn_msignup").equalsIgnoreCase(root.getViewIdResourceName()));
    }

    public boolean isMobileNumberFieldClicked(AccessibilityNodeInfoCompat root) {
        return (mBaseId.concat("mobileNo").equalsIgnoreCase(root.getViewIdResourceName()));
    }

    public AccessibilityNodeInfoCompat findSearchBox(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "search_widget_textbox");
    }

    public AccessibilityNodeInfoCompat findSearchEditText(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "search_autoCompleteTextView");
    }

    public boolean isSplashScreenOpened(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "splash_screen_progressBar") != null;
    }

    public AccessibilityNodeInfoCompat findSignUpButtonInAccountsScreen(AccessibilityNodeInfoCompat root) {
        ViewIdTextModel model = new ViewIdTextModel("btn_msignup", "New to Flipkart?");
        return getNodeByViewIdAndText(root, model);
    }

    public AccessibilityNodeInfoCompat findSignUpButtonInNewAccountScreen(AccessibilityNodeInfoCompat root) {
        ViewIdTextModel model = new ViewIdTextModel("btn_msignup", "SIGN UP");
        return getNodeByViewIdAndText(root, model);
    }

    public AccessibilityNodeInfoCompat findEnterMobileNumberEditText(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "mobileNo");
    }

    public AccessibilityNodeInfoCompat findResendCode(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "resendCode");
    }

    public boolean isMobileVerificationScreenOpened(AccessibilityNodeInfoCompat root) {
        ViewIdTextModel model = new ViewIdTextModel("description_text", "We are trying to auto verify your number with an SMS sent");
        return getNodeByViewIdAndText(root, model) != null;
    }

    public boolean isOTPScreenOpened(AccessibilityNodeInfoCompat root) {
        ViewIdTextModel model = new ViewIdTextModel("description_text", "Please enter the verification code sent to");
        return (getNodeByViewIdAndText(root, model) != null);
    }

    public AccessibilityNodeInfoCompat getResendCodeButton(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "resendCode");
    }
}

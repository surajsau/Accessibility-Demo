package com.halfplatepoha.accesibility;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * Created by surajkumarsau on 06/06/16.
 */
public class FlipkartHelper extends BaseHelper {

    public FlipkartHelper(Finder finder, String baseId) {
        super(finder, baseId);
    }

    public boolean isFlipkartAppIconClicked(AccessibilityNodeInfoCompat root) {
        return "Flipkart".equalsIgnoreCase(root.getContentDescription().toString());
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

    public AccessibilityNodeInfoCompat findSignUpButton(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, "btn_msignup");
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
        return (getNodeByText(root, "Not received your code?") != null)
                && (getNodeByViewIdAndText(root, model) != null);
    }
}

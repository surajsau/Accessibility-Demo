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

    public boolean isFlipkartAppIconClicked(AccessibilityNodeInfoCompat root) {
        return (root.getContentDescription() != null ?
                "Flipkart".equalsIgnoreCase(root.getContentDescription().toString()):false);
    }

    public boolean isSignUpButtonClicked(AccessibilityNodeInfoCompat root) {
        return "btn_msignup".equalsIgnoreCase(root.getViewIdResourceName());
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

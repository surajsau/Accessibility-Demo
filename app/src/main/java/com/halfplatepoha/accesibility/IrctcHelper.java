package com.halfplatepoha.accesibility;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * Created by surajkumarsau on 05/06/16.
 */
public class IrctcHelper extends BaseHelper {

    private static final String BASE_ID = "com.irctc.main:id/";

    public IrctcHelper(Finder finder) {
        super(finder);
    }

    public void findUserName(AccessibilityNodeInfoCompat root) {
        ViewIdTextModel model = new ViewIdTextModel("textview_main", "Username");
    }
}

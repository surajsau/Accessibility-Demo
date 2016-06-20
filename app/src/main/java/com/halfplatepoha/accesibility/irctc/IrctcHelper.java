package com.halfplatepoha.accesibility.irctc;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import com.halfplatepoha.accesibility.BaseHelper;
import com.halfplatepoha.accesibility.Finder;
import com.halfplatepoha.accesibility.ViewIdTextModel;

/**
 * Created by surajkumarsau on 05/06/16.
 */
public class IrctcHelper extends BaseHelper {

    private static final String BASE_ID = "com.irctc.main:id/";

    public IrctcHelper(Finder finder) {
        super(finder, BASE_ID);
    }

    public void findUserName(AccessibilityNodeInfoCompat root) {
        ViewIdTextModel model = new ViewIdTextModel("textview_main", "Username");
    }
}

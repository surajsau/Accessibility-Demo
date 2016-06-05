package com.halfplatepoha.accesibility;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * Created by surajkumarsau on 05/06/16.
 */
public class SnapdealHelper extends BaseHelper {

    private int clickLevel;

    private static final String BASE_ID = "com.snapdeal.main:id/";

    public SnapdealHelper(Finder finder) {
        super(finder);
        clickLevel = 0;
    }

    public AccessibilityNodeInfoCompat findSearchBar(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, BASE_ID + "search_text_view");
    }

    public AccessibilityNodeInfoCompat findSearchEditText(AccessibilityNodeInfoCompat root) {
        return getNodeByViewId(root, BASE_ID + "search_edit_text");
    }

    private String getSearchString(AccessibilityNodeInfoCompat root) {
        AccessibilityNodeInfoCompat node = getNodeByViewId(root, BASE_ID + "search_text_view");
        return node.getText().toString();
    }

    public AccessibilityNodeInfoCompat findCardWithSearchName(AccessibilityNodeInfoCompat root) {
        return getNodeByText(root, getSearchString(root));
    }

    public int getClickLevel() {
        return clickLevel;
    }

    public void setClickLevel(int clickLevel) {
        this.clickLevel = clickLevel;
    }
}

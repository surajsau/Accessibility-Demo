package com.halfplatepoha.accesibility;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * Created by surajkumarsau on 05/06/16.
 */
public class BaseHelper {

    private Finder mFinder;

    public BaseHelper(Finder finder) {
        mFinder = finder;
    }

    public AccessibilityNodeInfoCompat getNodeByText(AccessibilityNodeInfoCompat root, String nodeText) {
        return mFinder.dfs(root, nodeText, SearchParameterType.TYPE_TEXT);
    }

    public AccessibilityNodeInfoCompat getNodeByViewId(AccessibilityNodeInfoCompat root, String viewId) {
        return mFinder.dfs(root, viewId, SearchParameterType.TYPE_VIEW_RESOURCE_ID);
    }

    public AccessibilityNodeInfoCompat getNodeByViewIdAndText(AccessibilityNodeInfoCompat root, ViewIdTextModel model) {
        return mFinder.dfs(root, model, SearchParameterType.TYPE_VIEW_RESOURCE_ID_AND_TEXT);
    }

}

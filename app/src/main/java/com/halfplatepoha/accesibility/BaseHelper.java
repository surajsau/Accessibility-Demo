package com.halfplatepoha.accesibility;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * Created by surajkumarsau on 05/06/16.
 *
 * Base class for all App helper classes
 */

public class BaseHelper {

    public String mBaseId;

    private Finder mFinder;

    /**
     * @param finder **instead of passing reference of Finder always, an instance of it
     *               can be kept here itself
     * @param baseId basically the package name. Can be checked in logs
     */
    public BaseHelper(Finder finder, String baseId) {
        mFinder = finder;
        mBaseId = baseId;
    }

    public AccessibilityNodeInfoCompat getNodeByText(AccessibilityNodeInfoCompat root, String nodeText) {
        return mFinder.dfs(root, nodeText, SearchParameterType.TYPE_TEXT);
    }

    public AccessibilityNodeInfoCompat getNodeByViewId(AccessibilityNodeInfoCompat root, String viewId) {
        return mFinder.dfs(root, mBaseId + viewId, SearchParameterType.TYPE_VIEW_RESOURCE_ID);
    }

    public AccessibilityNodeInfoCompat getNodeByViewIdAndText(AccessibilityNodeInfoCompat root, ViewIdTextModel model) {
        if(model != null)
            model.setViewId(mBaseId + model.getViewId());
        return mFinder.dfs(root, model, SearchParameterType.TYPE_VIEW_RESOURCE_ID_AND_TEXT);
    }

    public AccessibilityNodeInfoCompat getNodeByDescription(AccessibilityNodeInfoCompat root, String description) {
        return mFinder.dfs(root, description, SearchParameterType.TYPE_DESCRIPTION);
    }

}

package com.halfplatepoha.accesibility;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * Created by surajkumarsau on 06/06/16.
 */
public class NodeIndicatorModel {

    private AccessibilityNodeInfoCompat node;
    private FrameView indicatorView;

    public NodeIndicatorModel(AccessibilityNodeInfoCompat node, FrameView indicatorView) {
        this.node = node;
        this.indicatorView = indicatorView;
    }

    public AccessibilityNodeInfoCompat getNode() {
        return node;
    }

    public void setNode(AccessibilityNodeInfoCompat node) {
        this.node = node;
    }

    public FrameView getIndicatorView() {
        return indicatorView;
    }

    public void setIndicatorView(FrameView indicatorView) {
        this.indicatorView = indicatorView;
    }
}

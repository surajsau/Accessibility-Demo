package com.halfplatepoha.accesibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by MacboolBro on 21/05/16.
 */
public class AccessibilityTestService extends AccessibilityService {

    private static final String TAG = "AS";

    private Queue<AccessibilityNodeInfoCompat> mQueue;

    private Finder mFinder;

    private SnapdealHelper mSDHelper;

    private Rect mRect;

    private WindowManager windowManager;

    private boolean isSearchClicked;
    private boolean isProductNameEntered;

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
        AccessibilityNodeInfoCompat nodeInfo = record.getSource();

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED: {
                if(nodeInfo != null) {
                    dfs(nodeInfo);

                    indicate(mSDHelper.findSearchBar(nodeInfo), "#000000");
                    indicate(mSDHelper.findSearchEditText(nodeInfo), "#FCAC0C");
                    indicate(mSDHelper.findCardWithSearchName(nodeInfo), "#000000");

//                    switch (mSDHelper.getClickLevel()) {
//                        case 0:
//                            indicate(mSDHelper.findSearchBar(nodeInfo), "#000000");
//                            mSDHelper.setClickLevel(1);
//                            break;
//
//                        case 1:
//                            indicate(mSDHelper.findSearchEditText(nodeInfo), "#FCAC0C");
//                            mSDHelper.setClickLevel(2);
//                            break;
//                    }

//                    AccessibilityNodeInfoCompat foundNode;
//                    foundNode = getNodeByViewId(nodeInfo, "com.bt.bms:id/viewPager");
//
//                    if(foundNode != null) {
//                        logInfo(foundNode);
//                        if(mRect == null)
//                            mRect = new Rect();
//
//                        foundNode.getBoundsInScreen(mRect);
//
//                        showIndicator();
//                    }


                }
            }
            break;

            case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                if(mRect != null)
                    hideIndicator();

                if(nodeInfo != null) {
                    Log.d("==", "==============");
                    logOthers(nodeInfo);
                    Log.d("==", "==============");
                }
            }

        }
    }

    private void indicate(AccessibilityNodeInfoCompat node, String color) {
        if(node != null) {
            logInfo(node);

            if(mRect == null)
                mRect = new Rect();

            node.getBoundsInScreen(mRect);

            showIndicator(color);
        }
    }

    @Override
    public void onInterrupt() {
        Log.v(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");
        mQueue = new LinkedList<>();

        mFinder = Finder.getInstance();
        mSDHelper = new SnapdealHelper(mFinder);
    }

    private void logInfo(AccessibilityNodeInfoCompat nodeInfo) {
        if(nodeInfo != null) {
            Log.i("child i", String.format("[resid] %s, [desc] %s, [type] %s, [text] %s",
                    nodeInfo.getViewIdResourceName(),
                    nodeInfo.getContentDescription(),
                    nodeInfo.getClassName(),
                    nodeInfo.getText()));
        }
    }

    private void logOthers(AccessibilityNodeInfoCompat nodeInfo) {
        if(nodeInfo != null) {
            Log.e("child e", String.format("[resid] %s, [desc] %s, [type] %s, [text] %s",
                    nodeInfo.getViewIdResourceName(),
                    nodeInfo.getContentDescription(),
                    nodeInfo.getClassName(),
                    nodeInfo.getText()));
        }
    }

    private void dfs(AccessibilityNodeInfoCompat root) {
        mQueue.add(root);

        while(!mQueue.isEmpty()) {
            AccessibilityNodeInfoCompat node = mQueue.remove();

            logOthers(node);

            if(node.getChildCount() > 0) {
                for(int i=0; i<node.getChildCount(); i++) {
                    if(node.getChild(i) != null)
                        mQueue.add(node.getChild(i));
                }
            }

        }

        mQueue.clear();
    }

    private void showIndicator(String color) {
        View rectView = new FrameView(this, mRect, color);

        ViewGroup.LayoutParams params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                mRect.left, mRect.top,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSPARENT);

        windowManager.addView(rectView, params);
    }

    private void hideIndicator() {
        View rectView = new RemoveFrameView(this, mRect);

        ViewGroup.LayoutParams params = new WindowManager.LayoutParams(mRect.height(),
                mRect.width(),
                mRect.left, mRect.top,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSPARENT);

        windowManager.addView(rectView, params);
    }

}

package com.halfplatepoha.accesibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
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

    private Queue<AccessibilityNodeInfo> mQueue;

    private boolean found;

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED: {

                AccessibilityNodeInfo nodeInfo = event.getSource();

                if(nodeInfo != null) {
                    AccessibilityNodeInfo foundNode;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        foundNode = getNodeByViewId(nodeInfo, "com.flipkart.android:id/search_widget_textbox");
                    } else {
                        foundNode  = getNodeByText(nodeInfo, "Search for Products, Brands");
                    }

                    if(foundNode != null) {
                        if(!found)
                            logInfo(foundNode);
                        found = true;
                    } else {
                        found = false;
                    }
                }
            }
            break;

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
    }

    private void logInfo(AccessibilityNodeInfo nodeInfo) {
        if(nodeInfo != null) {
            Log.i("child", String.format("[resid] %s, [desc] %s, [type] %s, [text] %s",
                    nodeInfo.getViewIdResourceName(),
                    nodeInfo.getContentDescription(),
                    nodeInfo.getClassName(),
                    nodeInfo.getText()));
        }
    }

    private AccessibilityNodeInfo getNodeByText(AccessibilityNodeInfo root, String nodeText) {
        return dfs(root, nodeText, SearchParameterType.TYPE_TEXT);
    }

    private AccessibilityNodeInfo getNodeByViewId(AccessibilityNodeInfo root, String viewId) {
        return dfs(root, viewId, SearchParameterType.TYPE_VIEW_RESOURCE_ID);
    }

    private AccessibilityNodeInfo dfs(AccessibilityNodeInfo root, @NonNull Object searchParameter, SearchParameterType searchType) {
        mQueue.add(root);

        while(!mQueue.isEmpty()) {
            AccessibilityNodeInfo node = mQueue.remove();

            switch (searchType) {
                case TYPE_VIEW_RESOURCE_ID: {
                    String param = (String)searchParameter;
                    if(param.equalsIgnoreCase(node.getViewIdResourceName())) {
                        mQueue.clear();
                        return node;
                    }
                }
                break;

                case TYPE_TEXT: {
                    String param = (String)searchParameter;
                    if(param.equalsIgnoreCase(node.getText().toString())) {
                        mQueue.clear();
                        return node;
                    }
                }
                break;
            }

            if(node.getChildCount() > 0) {
                for(int i=0; i<node.getChildCount(); i++) {
                    if(node.getChild(i) != null)
                        mQueue.add(node.getChild(i));
                }
            }

        }

        mQueue.clear();
        return null;
    }


}

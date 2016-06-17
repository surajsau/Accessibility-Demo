package com.halfplatepoha.accesibility;

import android.support.annotation.NonNull;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by surajkumarsau on 05/06/16.
 */
public class Finder {

    private static Finder mInstance;

    private Queue<AccessibilityNodeInfoCompat> mQueue;

    public static Finder getInstance() {
        if(mInstance == null)
            mInstance = new Finder();
        return mInstance;
    }

    private Finder() {
        mQueue = new LinkedList<>();
    }

    public AccessibilityNodeInfoCompat dfs(AccessibilityNodeInfoCompat root, @NonNull Object searchParameter, SearchParameterType searchType) {
        mQueue.add(root);

        while(!mQueue.isEmpty()) {
            AccessibilityNodeInfoCompat node = mQueue.remove();

//            logOthers(node);

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
                    if((node.getText() != null)
                            && (node.getText().toString()).contains(param)) {
                        mQueue.clear();
                        return node;
                    }
                }
                break;

                case TYPE_VIEW_RESOURCE_ID_AND_TEXT: {
                    ViewIdTextModel model = (ViewIdTextModel)searchParameter;
                    if(model.getViewId().equalsIgnoreCase(node.getViewIdResourceName())
                            && node.getText() != null
                            && (node.getText().toString()).contains(model.getText())) {
                        mQueue.clear();
                        return node;
                    }
                }
                break;

                case TYPE_DESCRIPTION: {
                    String param = (String)searchParameter;
                    if(node.getContentDescription() != null
                            && (node.getContentDescription().toString()).equalsIgnoreCase(param)) {
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

package com.halfplatepoha.accesibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import com.halfplatepoha.accesibility.flipkart.FlipkartHelper;
import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;
import com.halfplatepoha.accesibility.util.IConstants;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by MacboolBro on 21/05/16.
 */
public class AccessibilityTestService extends AccessibilityService implements IConstants {

    private static final String TAG = "AS";

    private Queue<AccessibilityNodeInfoCompat> mQueue;

    private MediaPlayer pingPlayer, hangoutPlayer;

    private Finder mFinder;

    private SnapdealHelper mSDHelper;

    private FlipkartHelper mFKHelper;

    private Rect mRect;

    private WindowManager windowManager;

    private AccessibilityNodeInfoCompat foundNode;

    private Rect foundRect;

    private FrameView rectView;

    private boolean isViewAdded;

    private boolean isSearchClicked;
    private boolean isProductNameEntered;

    private String callbackResult;

    private FlipkartLoginStages mStage;

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            callbackResult = intent.getStringExtra(CHOICE_RESULT);
            foundRect = intent.getParcelableExtra(RECT_TO_BE_INDICATED);
            postCallback();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pingPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ping);
        hangoutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hangout);
        windowManager = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
        AccessibilityNodeInfoCompat nodeInfo = record.getSource();

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED: {
                if(nodeInfo != null) {
//                    dfs(nodeInfo);

                switch (mStage) {
//                    case SPLASH_SCREEN:{
//                        if(mFKHelper.isSplashScreenOpened(nodeInfo)) {
//                            pingPlayer.start();
//                            mStage = FlipkartLoginStages.SIGNUP_SIGNIN_SCREEN;
//                        }
//                    }
//                    break;

                    case SIGNUP_SIGNIN_SCREEN:{
                        foundNode = mFKHelper.findSignUpButton(nodeInfo);

                        if(foundNode != null) {
                            foundNode.getBoundsInScreen(foundRect);
                            hangoutPlayer.start();
                            showDialog();
//                            mStage = FlipkartLoginStages.SIGNUP_SCREEN;
                        }
                    }
                    break;

                    case SIGNUP_SCREEN:{
                        AccessibilityNodeInfoCompat node = mFKHelper.findEnterMobileNumberEditText(nodeInfo);
                        if(node != null) {
//                            indicate(node);
                            showDialog();
//                            pingPlayer.start();
                            mStage = FlipkartLoginStages.SIGNUP_SCREEN_MOBILE_CLICK;
                        }
                    }
                    break;
                }
                }

            }
            break;

            case AccessibilityEvent.TYPE_VIEW_CLICKED: {

                switch (mStage) {
                    case ZERO:
                        if (nodeInfo != null && mFKHelper.isFlipkartAppIconClicked(nodeInfo)) {
                            mStage = FlipkartLoginStages.SIGNUP_SIGNIN_SCREEN;
                            pingPlayer.start();
                        }
                        break;

                    case SIGNUP_SIGNIN_SCREEN:{
                        if(nodeInfo != null && mFKHelper.isSignUpButtonClicked(nodeInfo)) {
                            mStage = FlipkartLoginStages.SIGNUP_SCREEN;
                        }
                    }
                }

            }
            break;

        }
    }

    private void indicate(AccessibilityNodeInfoCompat node) {
        if(node != null) {
//            logInfo(node);
            Rect rect = new Rect();
            node.getBoundsInScreen(rect);

            showIndicator(rect);
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
        pingPlayer.start();
        foundRect = new Rect();
        mStage = FlipkartLoginStages.ZERO;
        isViewAdded = false;
        mFKHelper = new FlipkartHelper(mFinder, "com.flipkart.android:id/");
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

    private void logOthersX(AccessibilityNodeInfoCompat nodeInfo) {
        if(nodeInfo != null) {
            Log.v("child e", String.format("[resid] %s, [desc] %s, [type] %s, [text] %s",
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

    private void dfsX(AccessibilityNodeInfoCompat root) {
        mQueue.add(root);

        while(!mQueue.isEmpty()) {
            AccessibilityNodeInfoCompat node = mQueue.remove();

            logOthersX(node);

            if(node.getChildCount() > 0) {
                for(int i=0; i<node.getChildCount(); i++) {
                    if(node.getChild(i) != null)
                        mQueue.add(node.getChild(i));
                }
            }

        }

        mQueue.clear();
    }

    private void showIndicator(Rect rect) {
//        if(rectView == null)
//            rectView = new FrameView(this, rect);
//
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(100,
//                100,
//                rect.left, rect.top,
//                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                        | WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                PixelFormat.TRANSPARENT
//        );
//        windowManager.addView(rectView, params);
        Intent indicatorIntent = new Intent();
    }

    private void hideIndicator() {
        if(rectView != null)
            rectView.setVisibility(View.GONE);
    }

    private void showDialog() {
        Intent intent = new Intent(AccessibilityTestService.this, HelperActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(SOURCE_STAGE, mStage);
        intent.putExtra(RECT_TO_BE_INDICATED, foundRect);
        startActivity(intent);
    }

    private void postCallback() {
        switch (callbackResult) {

            case ChoiceResults.NEW_USER:{
                showIndicator(foundRect);
            }
            break;

            case ChoiceResults.EXISTING_USER:{
                Intent intent = new Intent(AccessibilityTestService.this, HelperActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(SOURCE_STAGE, FlipkartLoginStages.SIGNUP_SIGNIN_SCREEN_EXISTING);
                startActivity(intent);
            }
            break;
        }
    }

}

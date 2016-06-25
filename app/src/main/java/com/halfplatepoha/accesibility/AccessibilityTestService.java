package com.halfplatepoha.accesibility;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.halfplatepoha.accesibility.flipkart.FlipkartHelper;
import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;
import com.halfplatepoha.accesibility.util.IConstants;
import com.halfplatepoha.accesibility.util.Utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by MacboolBro on 21/05/16.
 */
public class AccessibilityTestService extends AccessibilityService implements IConstants, ServiceConnection, TextToSpeech.OnInitListener {

    private static final String TAG = "AS";

    private Queue<AccessibilityNodeInfoCompat> mQueue;

    private MediaPlayer pingPlayer, hangoutPlayer;

    private TextToSpeech mTts;

    private Finder mFinder;

    private FlipkartHelper mFKHelper;

    private AccessibilityNodeInfoCompat foundNode, futureNeedMasterNode;

    private Rect foundRect;

    private boolean isBound;

    private String callbackResult;

    private FlipkartLoginStages mStage;

    private IndicatorService mIndicatorService;

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
        mTts = new TextToSpeech(getApplicationContext(), this);
        pingPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ping);
        hangoutPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hangout);

        Intent indicatorIntent = new Intent(this, IndicatorService.class);
        bindService(indicatorIntent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
        AccessibilityNodeInfoCompat nodeInfo = record.getSource();

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED: {
                if(nodeInfo != null) {
//                    if(nodeInfo != null) {
//                        Log.d("==", "==============");
//                        logOthers(nodeInfo);
//                        Log.d("==", "==============");
//                    }
//
//                    dfs(nodeInfo);
                    switch (mStage) {

                        case ZERO:{
                            foundNode = mFKHelper.findSignUpButtonInAccountsScreen(nodeInfo);

                            if(foundNode != null) {
                                foundNode.getBoundsInScreen(foundRect);
                                showDialog();
                            }
                        }
                        break;

                        case SIGNUP_SCREEN:{
                            foundNode = mFKHelper.findEnterMobileNumberEditText(nodeInfo);

                            if(foundNode != null) {
                                mStage = FlipkartLoginStages.SIGNUP_SCREEN_BEFORE_MOBILE_CLICK;
                                foundNode.getBoundsInScreen(foundRect);
                                showIndicator(foundRect);
                            }
                        }
                        break;

                        case SIGNUP_SCREEN_BEFORE_MOBILE_CLICK: {
                            foundNode = mFKHelper.findSignUpButtonInNewAccountScreen(nodeInfo);
                        }
                        break;

                        case AUTOVERIFICATION_SCREEN: {
                            if(nodeInfo != null && mFKHelper.isMobileVerificationScreenOpened(nodeInfo)) {
                                speak(mStage);
                                mStage = FlipkartLoginStages.POST_AUTOVERIFICATION_SCREEN;
                            }
                        }
                        break;

                        case POST_AUTOVERIFICATION_SCREEN:{
                            if(nodeInfo != null) {
                                if(mFKHelper.isOTPScreenOpened(nodeInfo)) {
                                    mStage = FlipkartLoginStages.OTP_SCREEN;
                                } else {
                                    //TODO: automatic verified
                                }
                            }
                        }
                        break;

                        case OTP_SCREEN:{
                            foundNode = mFKHelper.getResendCodeButton(nodeInfo);
                            if(foundNode != null)
                                showDialog();
                        }
                        break;
                    }
                }

            }
            break;

            case AccessibilityEvent.TYPE_VIEW_CLICKED: {

//                if(nodeInfo != null) {
//                    Log.d("==", "==============");
//                    logOthers(nodeInfo);
//                    Log.d("==", "==============");
//                }

                switch (mStage) {
                    case SIGNUP_SIGNIN_SCREEN:{
                        if(nodeInfo != null && mFKHelper.isSignUpButtonClicked(nodeInfo)) {
                            hideIndicator();
                            mStage = FlipkartLoginStages.SIGNUP_SCREEN;
                        }
                    }
                    break;

                    case SIGNUP_SCREEN_AFTER_MOBILE_CLICK: {
                        if(nodeInfo != null && mFKHelper.isSignUpButtonClicked(nodeInfo)) {
                            hideIndicator();
                            mStage = FlipkartLoginStages.AUTOVERIFICATION_SCREEN;
                        }
                    }
                    break;
                }

            }
            break;

            case AccessibilityEvent.TYPE_VIEW_FOCUSED: {
                switch (mStage) {
                    case SIGNUP_SCREEN_BEFORE_MOBILE_CLICK: {
                        if(nodeInfo != null && mFKHelper.isMobileNumberFieldClicked(nodeInfo)) {
                            hideIndicator();
                            if(foundNode != null) {
                                mStage = FlipkartLoginStages.SIGNUP_SCREEN_AFTER_MOBILE_CLICK;
                                foundNode.getBoundsInScreen(foundRect);
                                foundRect.top -= 400;
                                showIndicator(foundRect);
                            }
                        }
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

    //--on accessibility service connected
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");
        mQueue = new LinkedList<>();

        mFinder = Finder.getInstance();
        pingPlayer.start();
        foundRect = new Rect();
        mStage = FlipkartLoginStages.ZERO;
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
        if(isBound)
            mIndicatorService.showIndicator(rect, mStage);
    }

    private void hideIndicator() {
        if(isBound)
            mIndicatorService.hideIndicator();
    }

    private void showDialog() {
        speak(mStage);

        Intent intent = new Intent(AccessibilityTestService.this, HelperActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(SOURCE_STAGE, mStage);
        intent.putExtra(RECT_TO_BE_INDICATED, foundRect);
        startActivity(intent);
    }

    private void speak(FlipkartLoginStages stage) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mTts.speak(Utils.getSpeechString(stage), TextToSpeech.QUEUE_FLUSH, null, null);
        else
            mTts.speak(Utils.getSpeechString(stage), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void postCallback() {
        switch (callbackResult) {

            case ChoiceResults.NEW_USER:{
                mStage = FlipkartLoginStages.SIGNUP_SIGNIN_SCREEN;
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

            case ChoiceResults.OTP_YES: {

            }
            break;

            case ChoiceResults.OTP_NO: {
                foundNode.getBoundsInScreen(foundRect);
                showIndicator(foundRect);
            }
            break;
        }
    }

    //--on indicator binder service connected
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        IndicatorService.IndicatorBinder binder = (IndicatorService.IndicatorBinder) service;
        mIndicatorService = binder.getService();
        isBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isBound = false;
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
        } else {
            Log.e(TAG, "Couldn't initialize text to speech");
        }
    }
}

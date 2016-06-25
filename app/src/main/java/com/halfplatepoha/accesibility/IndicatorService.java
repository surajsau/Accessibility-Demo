package com.halfplatepoha.accesibility;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.halfplatepoha.accesibility.flipkart.FlipkartLoginStages;
import com.halfplatepoha.accesibility.util.IConstants;
import com.halfplatepoha.accesibility.util.Utils;

import java.util.Locale;

/**
 * Created by surajkumarsau on 18/06/16.
 */
public class IndicatorService extends Service implements TextToSpeech.OnInitListener {

    private static final String TAG = IndicatorService.class.getSimpleName();

    private WindowManager windowManager;
    private LinearLayout linearLayout;

    private Rect mRect;

    private IndicatorBinder mBinder;

    private TextToSpeech mTts;

    private boolean isAttached;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mTts = new TextToSpeech(getApplicationContext(), this);

        if(mBinder == null)
            mBinder = new IndicatorBinder();
        return mBinder;
    }

    public void showIndicator(Rect rect, FlipkartLoginStages stage) {
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams(rect.width(),
                rect.height(),
                rect.left,
                rect.top,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);
        windowParams.gravity = Gravity.TOP|Gravity.LEFT;

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.layout_indicator, null);

        windowManager.addView(linearLayout, windowParams);

        isAttached = true;

        speak(stage);
    }

    private void speak(FlipkartLoginStages stage) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mTts.speak(Utils.getSpeechString(stage), TextToSpeech.QUEUE_FLUSH, null, null);
        else
            mTts.speak(Utils.getSpeechString(stage), TextToSpeech.QUEUE_FLUSH, null);
    }

    public void hideIndicator() {
        if(isAttached) {
            windowManager.removeViewImmediate(linearLayout);
            isAttached = false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= 23) {
            if(! (Settings.canDrawOverlays(getBaseContext())) ){
                return;
            }
        }
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
        } else {
            Log.e(TAG, "Couldn't initialize text to speech");
        }
    }

    public class IndicatorBinder extends Binder {
        public IndicatorService getService() {
            return IndicatorService.this;
        }
    }

    @Override
    public void onDestroy() {
        if(mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }
}

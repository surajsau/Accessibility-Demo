package com.halfplatepoha.accesibility;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
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

import com.halfplatepoha.accesibility.util.IConstants;

/**
 * Created by surajkumarsau on 18/06/16.
 */
public class IndicatorService extends Service {

    private WindowManager windowManager;
    private LinearLayout linearLayout;

    private Rect mRect;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null)
            mRect = intent.getParcelableExtra(IConstants.RECT_TO_BE_INDICATED);
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams(mRect.width(),
                mRect.height(),
                mRect.left,
                mRect.top,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);
        windowParams.gravity = Gravity.TOP|Gravity.LEFT;

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.layout_indicator, null);

        windowManager.addView(linearLayout, windowParams);
        return START_STICKY;
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

}

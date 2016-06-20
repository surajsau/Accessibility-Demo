package com.halfplatepoha.accesibility;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.halfplatepoha.accesibility.util.IConstants;

/**
 * Created by surajkumarsau on 18/06/16.
 */
public class IndicatorService extends Service {

    private WindowManager mWindowManager;
    private View indicatorView;
    private WindowManager.LayoutParams mParams;
    private Rect mRect;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        indicatorView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_indicator, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            mRect = intent.getParcelableExtra(IConstants.RECT_TO_BE_INDICATED);

            mParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    mRect.left, mRect.top,
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    PixelFormat.TRANSPARENT
            );

            mWindowManager.addView(indicatorView, mParams);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}

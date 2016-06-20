package com.halfplatepoha.accesibility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by surajkumarsau on 27/05/16.
 */
public class FrameView extends View {

    private Rect mRect;
    private Bitmap mBitmap;

    private boolean isClear;

    public FrameView(Context context, Rect rect) {
        super(context);
//        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ring);
        mRect = rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        canvas.drawRect(0, 0, mRect.width(), mRect.height(), paint);
    }

    public void clearScreen(Rect rect) {
        mRect = rect;
        isClear = true;
        invalidate();
    }
}

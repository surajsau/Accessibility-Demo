package com.halfplatepoha.accesibility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by surajkumarsau on 27/05/16.
 */
public class FrameView extends View {

    private Rect mRect;
    private Paint mPaint;
    private String mColor;

    public FrameView(Context context, Rect rect, String color) {
        super(context);
        mRect = rect;
        mColor = color;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        canvas.drawRect(0, 0, mRect.width(), mRect.height(), mPaint);
    }
}

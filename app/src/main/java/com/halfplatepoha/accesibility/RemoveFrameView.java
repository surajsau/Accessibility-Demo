package com.halfplatepoha.accesibility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by surajkumarsau on 27/05/16.
 */
public class RemoveFrameView extends View {

    private Rect mRect;
    private Paint mPaint;

    public RemoveFrameView(Context context, Rect rect) {
        super(context);
        mRect = rect;

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0, 0, mRect.width(), mRect.height(), mPaint);
    }
}

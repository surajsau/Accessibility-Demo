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

    private Paint mPaint;

    public RemoveFrameView(Context context) {
        super(context);

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
    }
}

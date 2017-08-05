package com.grishberg.samplewatch.presentation.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.WindowInsets;

import com.grishberg.samplewatch.R;

/**
 * Created by grishberg on 05.08.17.
 */

public class BackgroundView implements WatchFaceViewDelegate {
    Paint backgroundPaint;

    public BackgroundView(Context context) {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(ContextCompat.getColor(context, R.color.background));
    }

    @Override
    public void draw(Canvas canvas, Rect bounds, boolean isAmbient) {
        if (isAmbient) {
            canvas.drawColor(Color.BLACK);
        } else {
            canvas.drawRect(0, 0, bounds.width(), bounds.height(), backgroundPaint);
        }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }

    @Override
    public void onAmbientModeChanged(boolean inAmbientMode) {

    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {
        // do nothing
    }
}

package com.grishberg.samplewatch.presentation.ui.views;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.WindowInsets;

/**
 * Created by grishberg on 05.08.17.
 */

public interface WatchFaceViewDelegate {
    void draw(Canvas canvas, Rect bounds, boolean isAmbient);

    void onApplyWindowInsets(WindowInsets insets);

    void onVisibilityChanged(boolean visible);

    void onAmbientModeChanged(boolean inAmbientMode);
}

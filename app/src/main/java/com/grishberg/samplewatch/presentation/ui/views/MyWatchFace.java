/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grishberg.samplewatch.presentation.ui.views;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowInsets;
import android.widget.Toast;

import com.grishberg.samplewatch.R;
import com.grishberg.samplewatch.presentation.presenters.TimerProvider;

import java.util.ArrayList;

/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 */
public class MyWatchFace extends CanvasWatchFaceService {
    private static final String TAG = MyWatchFace.class.getSimpleName();

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine implements Invalidator {

        private ArrayList<WatchFaceViewDelegate> views = new ArrayList<>();
        private TimerProvider timerProvider;

        @Override
        public void onCreate(SurfaceHolder holder) {
            Log.d(TAG, "onCreate: ");
            super.onCreate(holder);

            setWatchFaceStyle(new WatchFaceStyle.Builder(MyWatchFace.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_VARIABLE)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false)
                    .setAcceptsTapEvents(true)
                    .build());
            Resources resources = MyWatchFace.this.getResources();

            timerProvider = new TimerProvider();
            views.add(new BackgroundView(MyWatchFace.this));
            views.add(new WatchView(MyWatchFace.this, resources, this, timerProvider));
            views.add(new WeatherView(MyWatchFace.this, resources));
            views.add(new StepsCounterView(MyWatchFace.this, resources));
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy: ");
            timerProvider.stop();
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d(TAG, "onVisibilityChanged: ");
            super.onVisibilityChanged(visible);

            for (WatchFaceViewDelegate viewDelegate : views) {
                viewDelegate.onVisibilityChanged(visible);
            }

            if (visible) {

                // Update time zone in case it changed while we weren't visible.
                invalidate();
            } else {

            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            timerProvider.updateTimer();
        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            Log.d(TAG, "onApplyWindowInsets: ");
            super.onApplyWindowInsets(insets);

            for (WatchFaceViewDelegate viewDelegate : views) {
                viewDelegate.onApplyWindowInsets(insets);
            }
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            Log.d(TAG, "onPropertiesChanged: ");
            super.onPropertiesChanged(properties);
            for (WatchFaceViewDelegate viewDelegate : views) {
                if (viewDelegate instanceof WearPropertiesWatcher) {
                    ((WearPropertiesWatcher) viewDelegate).onPropertiesChanged(properties);
                }
            }
        }

        @Override
        public void onTimeTick() {
            Log.d(TAG, "onTimeTick: ");
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            Log.d(TAG, "onAmbientModeChanged: inAmbientMode = " + inAmbientMode);
            super.onAmbientModeChanged(inAmbientMode);
            for (WatchFaceViewDelegate viewDelegate : views) {
                viewDelegate.onAmbientModeChanged(inAmbientMode);
            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer();
        }

        /**
         * Captures tap event (and tap type) and toggles the background color if the user finishes
         * a tap.
         */
        @Override
        public void onTapCommand(int tapType, int x, int y, long eventTime) {
            switch (tapType) {
                case TAP_TYPE_TOUCH:
                    // The user has started touching the screen.
                    break;
                case TAP_TYPE_TOUCH_CANCEL:
                    // The user has started a different gesture or otherwise cancelled the tap.
                    break;
                case TAP_TYPE_TAP:
                    // The user has completed the tap gesture.
                    // TODO: Add code to handle the tap gesture.
                    Toast.makeText(getApplicationContext(), R.string.message, Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            invalidate();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            for (WatchFaceViewDelegate view : views) {
                view.draw(canvas, bounds, isInAmbientMode());
            }
        }

        private void updateTimer() {
            timerProvider.stop();
            if (shouldTimerBeRunning()) {
                timerProvider.updateTimer();
            }
        }

        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }
    }
}

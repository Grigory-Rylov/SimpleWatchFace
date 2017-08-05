package com.grishberg.samplewatch.presentation.presenters;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * Created by grishberg on 05.08.17.
 */

public class TimerProvider {
    /**
     * Update rate in milliseconds for interactive mode. We update once a second since seconds are
     * displayed in interactive mode.
     */
    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1);

    /**
     * Handler message id for updating the time periodically in interactive mode.
     */
    private static final int MSG_UPDATE_TIME = 0;
    private WeakReference<OnTickListener> mWeakReference;
    private boolean shouldTimerBeRunning;

    @Nullable
    private OnTickListener onTickListener;

    public TimerProvider() {
        mWeakReference = new WeakReference<>(null);
    }

    final Handler updateTimeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            OnTickListener listener = mWeakReference.get();
            if (listener != null) {
                switch (msg.what) {
                    case MSG_UPDATE_TIME:
                        listener.onTick();
                        if (shouldTimerBeRunning) {
                            long timeMs = System.currentTimeMillis();
                            long delayMs = INTERACTIVE_UPDATE_RATE_MS
                                    - (timeMs % INTERACTIVE_UPDATE_RATE_MS);
                            updateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
                        }

                        break;
                }
            }
            return false;
        }
    });

    public void stop() {
        shouldTimerBeRunning = false;
        updateTimeHandler.removeMessages(MSG_UPDATE_TIME);
    }

    public void updateTimer() {
        shouldTimerBeRunning = true;
        updateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
    }

    public void registerTickListener(OnTickListener listener) {
        onTickListener = listener;
        mWeakReference = new WeakReference<>(onTickListener);
    }

    public interface OnTickListener {
        void onTick();
    }
}

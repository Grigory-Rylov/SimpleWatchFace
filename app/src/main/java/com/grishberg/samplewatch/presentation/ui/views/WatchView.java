package com.grishberg.samplewatch.presentation.ui.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.WindowInsets;

import com.github.mvpstatelib.framework.state.ViewState;
import com.grishberg.samplewatch.R;
import com.grishberg.samplewatch.presentation.presenters.TimerProvider;
import com.grishberg.samplewatch.presentation.presenters.WatchPresenter;
import com.grishberg.samplewatch.presentation.ui.common.BaseWatchView;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.support.wearable.watchface.WatchFaceService.PROPERTY_LOW_BIT_AMBIENT;

/**
 * Created by grishberg on 05.08.17.
 */

public class WatchView extends BaseWatchView<WatchPresenter> implements WatchFaceViewDelegate,
        WearPropertiesWatcher, TimerProvider.OnTickListener {
    private Paint textPaint;
    private final Context context;
    private final Resources resources;
    private Calendar mCalendar;
    private float mXOffset;
    private float mYOffset;
    private final Invalidator invalidator;
    private boolean mAmbient;
    private final TimerProvider timerProvider;
    /**
     * Whether the display supports fewer bits for each color in ambient mode. When true, we
     * disable anti-aliasing in ambient mode.
     */
    boolean mLowBitAmbient;

    private final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCalendar.setTimeZone(TimeZone.getDefault());
            invalidator.invalidate();
        }
    };

    private boolean mRegisteredTimeZoneReceiver = false;

    public WatchView(Context context,
                     Resources resources,
                     Invalidator invalidator,
                     TimerProvider timerProvider) {
        super();
        this.context = context;
        this.resources = resources;
        this.invalidator = invalidator;
        this.timerProvider = timerProvider;
        timerProvider.registerTickListener(this);
        textPaint = new Paint();
        textPaint = createTextPaint(ContextCompat.getColor(context, R.color.digital_text));
        mCalendar = Calendar.getInstance();
        mYOffset = resources.getDimension(R.dimen.digital_y_offset);
    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {
        boolean isRound = insets.isRound();
        float textSize = resources.getDimension(isRound
                ? R.dimen.digital_text_size_round : R.dimen.digital_text_size);
        textPaint.setTextSize(textSize);
        mXOffset = resources.getDimension(isRound
                ? R.dimen.digital_x_offset_round : R.dimen.digital_x_offset);
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if (visible) {
            registerReceiver();
            mCalendar.setTimeZone(TimeZone.getDefault());
        } else {
            unregisterReceiver();
        }
    }

    private void registerReceiver() {
        if (mRegisteredTimeZoneReceiver) {
            return;
        }
        mRegisteredTimeZoneReceiver = true;
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
        context.registerReceiver(mTimeZoneReceiver, filter);
    }

    private void unregisterReceiver() {
        if (!mRegisteredTimeZoneReceiver) {
            return;
        }
        mRegisteredTimeZoneReceiver = false;
        context.unregisterReceiver(mTimeZoneReceiver);
    }

    @Override
    public void onAmbientModeChanged(boolean inAmbientMode) {
        if (mAmbient != inAmbientMode) {
            mAmbient = inAmbientMode;
            if (mLowBitAmbient) {
                textPaint.setAntiAlias(!inAmbientMode);
            }
            invalidator.invalidate();
        }
    }

    @Override
    public void onPropertiesChanged(Bundle properties) {
        mLowBitAmbient = properties.getBoolean(PROPERTY_LOW_BIT_AMBIENT, false);
    }

    @Override
    protected WatchPresenter providePresenter() {
        return new WatchPresenter();
    }

    @Override
    public void draw(Canvas canvas, Rect bounds, boolean isAmbient) {
        long now = System.currentTimeMillis();
        mCalendar.setTimeInMillis(now);

        String text = isAmbient
                ? String.format(Locale.US, "%d:%02d", mCalendar.get(Calendar.HOUR),
                mCalendar.get(Calendar.MINUTE))
                : String.format(Locale.US, "%d:%02d:%02d", mCalendar.get(Calendar.HOUR),
                mCalendar.get(Calendar.MINUTE), mCalendar.get(Calendar.SECOND));
        canvas.drawText(text, mXOffset, mYOffset, textPaint);
    }

    @Override
    public void onStateUpdated(ViewState model) {

    }

    @Override
    public void onTick() {
        invalidator.invalidate();
    }
}

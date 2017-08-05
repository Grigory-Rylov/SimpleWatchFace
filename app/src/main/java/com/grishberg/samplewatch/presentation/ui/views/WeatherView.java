package com.grishberg.samplewatch.presentation.ui.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.WindowInsets;

import com.github.mvpstatelib.framework.state.ViewState;
import com.grishberg.samplewatch.R;
import com.grishberg.samplewatch.presentation.presenters.WeatherPresenter;
import com.grishberg.samplewatch.presentation.ui.common.BaseWatchView;

import java.util.Locale;

/**
 * Created by grishberg on 05.08.17.
 */

public class WeatherView extends BaseWatchView<WeatherPresenter> implements WatchFaceViewDelegate {
    private final float weatherXOffset;
    private final float weatherYOffset;
    private Paint weatherTextPaint;
    private String weatherAsString = "32.5";

    public WeatherView(Context context, Resources resources) {
        super();
        weatherTextPaint = new Paint();
        weatherTextPaint = createTextPaint(ContextCompat.getColor(context, R.color.digital_text));
        float weatherTextSize = resources.getDimension(R.dimen.weather_text_size_round);
        weatherTextPaint.setTextSize(weatherTextSize);

        weatherXOffset = resources.getDimension(R.dimen.weather_x_offset_round);
        weatherYOffset = resources.getDimension(R.dimen.weather_y_offset_round);
    }

    @Override
    protected WeatherPresenter providePresenter() {
        return new WeatherPresenter();
    }

    @Override
    public void draw(Canvas canvas, Rect bounds, boolean isAmbient) {
        String weatherText = String.format(Locale.US, "%s C", weatherAsString);
        canvas.drawText(weatherText, weatherXOffset, weatherYOffset, weatherTextPaint);
    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {

    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }

    @Override
    public void onAmbientModeChanged(boolean inAmbientMode) {

    }

    @Override
    public void onStateUpdated(ViewState model) {

    }
}

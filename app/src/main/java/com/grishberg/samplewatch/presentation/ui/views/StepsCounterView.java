package com.grishberg.samplewatch.presentation.ui.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.WindowInsets;

import com.github.mvpstatelib.framework.state.ViewState;
import com.grishberg.samplewatch.presentation.presenters.StepsCounterPresenter;
import com.grishberg.samplewatch.presentation.ui.common.BaseWatchView;

/**
 * Created by grishberg on 05.08.17.
 */

public class StepsCounterView extends BaseWatchView<StepsCounterPresenter> implements WatchFaceViewDelegate {
    private final Context context;
    private final Resources resources;

    public StepsCounterView(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    protected StepsCounterPresenter providePresenter() {
        return new StepsCounterPresenter();
    }

    @Override
    public void draw(Canvas canvas, Rect bounds, boolean isAmbient) {

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

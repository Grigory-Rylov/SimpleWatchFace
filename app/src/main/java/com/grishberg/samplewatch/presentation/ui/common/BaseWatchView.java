package com.grishberg.samplewatch.presentation.ui.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import com.github.mvpstatelib.framework.presenter.BaseMvpPresenter;
import com.github.mvpstatelib.framework.state.StateObserver;
import com.github.mvpstatelib.framework.state.ViewState;
import com.github.mvpstatelib.framework.view.DelegateTagHolder;
import com.github.mvpstatelib.framework.view.MvpHelper;

/**
 * Created by grishberg on 05.08.17.
 */

public abstract class BaseWatchView<P extends BaseMvpPresenter>
        implements StateObserver<ViewState>, DelegateTagHolder<ViewState> {
    protected static final Typeface NORMAL_TYPEFACE =
            Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);

    private MvpHelper<P> helper;

    public BaseWatchView() {
        helper = new MvpHelper<>(this);
        if (helper.getPresenter() == null) {
            helper.setPresenter(providePresenter());
        }
    }

    @NonNull
    protected abstract P providePresenter();

    protected P getPresenter() {
        return helper.getPresenter();
    }

    protected void onAttached() {
        helper.onAttachedToWindow();
    }

    protected void onDetached() {
        helper.onDetachedFromWindow();
    }

    @Override
    public int getId() {
        return 0;
    }

    protected Paint createTextPaint(int textColor) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTypeface(NORMAL_TYPEFACE);
        paint.setAntiAlias(true);
        return paint;
    }
}

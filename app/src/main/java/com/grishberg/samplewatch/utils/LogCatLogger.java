package com.grishberg.samplewatch.utils;

import android.util.Log;

/**
 * Created by grishberg on 03.08.17.
 */

public class LogCatLogger implements Logger {
    @Override
    public void d(final String tag, final String message) {
        Log.d(tag, message);
    }

    @Override
    public void e(final String tag, final String message) {
        Log.e(tag, message);
    }

    @Override
    public void e(final String tag, final String message, final Throwable throwable) {
        Log.e(tag, message, throwable);
    }
}

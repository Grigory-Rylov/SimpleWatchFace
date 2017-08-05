package com.grishberg.samplewatch.utils;

/**
 * Created by grishberg on 03.08.17.
 */

public interface Logger {
    void d(String tag, String message);

    void e(String tag, String message);

    void e(String tag, String message, Throwable throwable);
}

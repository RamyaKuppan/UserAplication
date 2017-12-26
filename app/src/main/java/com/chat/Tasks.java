package com.chat;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.Executor;

/**
 * Created by Ramya on 7/18/2017.
 */

public class Tasks
{
    private Tasks() {
        throw new UnsupportedOperationException();
    }

    private static final String ERROR_MSG = "you cannot use a custom executor on pre honeycomb devices";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion) {
        executeInBackground(context, backgroundWork, completion, AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion, Executor executor) {
        new Task<T>(context, backgroundWork, completion).executeOnExecutor(executor);
    }
}

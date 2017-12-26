package com.chat;

import android.os.AsyncTask;

/**
 * Created by Ramya on 7/18/2017.
 */

abstract class AbstractTask<T> extends AsyncTask<Void, Void, T> {

    private BackgroundWork<T> backgroundWork;
    private Exception exception;

    /*package*/ AbstractTask(BackgroundWork<T> backgroundWork) {
        this.backgroundWork = backgroundWork;
    }

    @Override
    protected final T doInBackground(Void... params) {
        try {
            return backgroundWork.doInBackground();
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected final void onPostExecute(T result) {
        if (!isCancelled()) {
            if (exception == null) {
                onSuccess(result);
            } else {
                onError(exception);
            }
        }
    }

    protected abstract void onSuccess(T result);
    protected abstract void onError(Exception e);
}

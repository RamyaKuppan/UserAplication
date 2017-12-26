package com.chat;

public interface BackgroundWork<T> {
    T doInBackground() throws Exception;
}
package se.kth.networking.hangman.http;

/**
 * Created by victoraxelsson on 2016-11-24.
 */

public interface OnResponse<T> {
    void onResponse(T res);
}

package com.example.credits.client.common;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncCallbackWithFailureHandling<T> implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
        Window.alert("RPC Failed");
    }
}

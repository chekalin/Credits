package com.example.credits.shared.services;

import com.example.credits.client.calculator.CalculatorModel;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CalculatorServiceAsync {
    void load(AsyncCallback<CalculatorModel> callback);
    void calculate(int days, int amount, boolean isFirst, AsyncCallback<CalculatorModel> async);
}

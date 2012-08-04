package com.example.credits.shared.services;

import com.example.credits.client.calculator.CalculatorModel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface CalculatorServiceAsync {
    void load(AsyncCallback<CalculatorModel> callback);
    void calculate(int days, int amount, boolean isFirst, AsyncCallback<CalculatorModel> async);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void submit(int numberOfDays, int amount, boolean isFirst, AsyncCallback<Void> async);
}

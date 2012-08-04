package com.example.credits.shared.services;

import com.example.credits.client.calculator.CalculatorModel;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/calculatorService")
public interface CalculatorService extends RemoteService {
    CalculatorModel load();
    CalculatorModel calculate(int days, int amount, boolean isFirst);
}

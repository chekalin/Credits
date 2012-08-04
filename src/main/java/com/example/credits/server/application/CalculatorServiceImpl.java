package com.example.credits.server.application;

import com.example.credits.client.calculator.CalculatorModel;
import com.example.credits.shared.services.CalculatorService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {
    private static final Integer DEFAULT_AMOUNT = 200;
    private static final Integer DEFAULT_NUMBER_OF_DAYS = 30;

    @Override
    public CalculatorModel calculate(int days, int amount, boolean isFirst) {
        CalculatorModel model = new CalculatorModel();
        model.setAmount(amount);
        model.setNumberOfDays(days);
        model.setComission(new BigDecimal(3.14));
        model.setDeadline(calculateDeadline(days));
        model.setProlongation7(new BigDecimal(10));
        model.setProlongation14(new BigDecimal(15));
        model.setProlongation30(new BigDecimal(20));
        model.setFirst(isFirst);
        return model;
    }

    private Date calculateDeadline(int days) {
        Date deadline = new Date();
        deadline = DateUtils.addDays(deadline, days);
        return deadline;
    }

    @Override
    public CalculatorModel load() {
        return calculate(DEFAULT_NUMBER_OF_DAYS, DEFAULT_AMOUNT, true);
    }
}

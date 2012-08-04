package com.example.credits.server.application;

import com.example.credits.client.calculator.CalculatorModel;
import com.example.credits.server.persistence.CreditRepository;
import com.example.credits.shared.domain.Credit;
import com.example.credits.shared.services.CalculatorService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {
    private static final Integer DEFAULT_AMOUNT = 200;
    private static final Integer DEFAULT_NUMBER_OF_DAYS = 30;
    public static final BigDecimal COMMISSION_RATE_FOR_30_DAYS = new BigDecimal(0.1);
    public static final BigDecimal EXTENSION_PERCENT_7_DAYS = new BigDecimal(0.06);
    public static final BigDecimal EXTENSION_PERCENT_14_DAYS = new BigDecimal(0.09);
    public static final BigDecimal EXTENSION_PERCENT_30_DAYS = new BigDecimal(0.15);

    @Autowired private CreditRepository creditRepository;

    @Override
    public CalculatorModel calculate(int days, int amount, boolean isFirst) {
        CalculatorModel model = new CalculatorModel();
        model.setAmount(amount);
        model.setNumberOfDays(days);
        model.setComission(calculateCommission(amount, days, isFirst));
        model.setDeadline(calculateDeadline(days));
        model.setProlongation7(new BigDecimal(amount).multiply(EXTENSION_PERCENT_7_DAYS));
        model.setProlongation14(new BigDecimal(amount).multiply(EXTENSION_PERCENT_14_DAYS));
        model.setProlongation30(new BigDecimal(amount).multiply(EXTENSION_PERCENT_30_DAYS));
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

    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public void submit(int numberOfDays, int amount, boolean isFirst) {
        Credit credit = new Credit();
        credit.setAmount(amount);
        credit.setNumberOfDays(numberOfDays);
        credit.setFirst(isFirst);
        credit.setCommission(calculateCommission(amount, numberOfDays, isFirst));
        creditRepository.persist(credit);
    }

    BigDecimal calculateCommission(int amount, int numberOfDays, boolean first) {
        if (first && amount <= 200) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal rateForPeriod = new BigDecimal(numberOfDays)
                    .divide(new BigDecimal(30), 5, BigDecimal.ROUND_HALF_UP)
                    .multiply(COMMISSION_RATE_FOR_30_DAYS);
            return new BigDecimal(amount).multiply(rateForPeriod);
        }
    }
}

package com.example.credits.server.application;

import com.example.credits.client.calculator.CalculatorModel;
import com.example.credits.server.persistence.CreditRepository;
import com.example.credits.shared.domain.Credit;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;

import static com.example.credits.server.persistence.Matchers.closeTo;
import static com.example.credits.server.persistence.Matchers.sameDay;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CalculatorServiceImplTest {

    CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
    @Mock private CreditRepository creditRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        ReflectionTestUtils.setField(calculatorService, "creditRepository", creditRepository);
    }

    @Test
    public void shouldCalculateDeadline() throws Exception {
        CalculatorModel model = calculatorService.calculate(30, 200, true);
        assertThat(model.getDeadline(), is(sameDay(DateUtils.addDays(new Date(), 30))));
    }

    @Test
    public void forFirstCreditBelow200CommissionShouldBeZero() throws Exception {
        CalculatorModel model = calculatorService.calculate(30, 200, true);
        assertThat(model.getComission(), is(closeTo(BigDecimal.ZERO)));
    }

    @Test
    public void forFirstCreditAbove200CommissionShouldBe10PercentPer30Days() throws Exception {
        CalculatorModel model = calculatorService.calculate(30, 300, true);
        assertThat(model.getComission(), is(closeTo(new BigDecimal(30.00))));
    }

    @Test
    public void forNonFirstCreditCommissionShouldAlwaysBe10PercentPer30Days() throws Exception {
        CalculatorModel model = calculatorService.calculate(30, 10, false);
        assertThat(model.getComission(), is(closeTo(new BigDecimal(1.00))));
    }

    @Test
    public void shouldCalculateProlongationRates() throws Exception {
        CalculatorModel model = calculatorService.calculate(30, 100, false);
        assertThat(model.getProlongation7(), is(closeTo(new BigDecimal(6.00))));
        assertThat(model.getProlongation14(), is(closeTo(new BigDecimal(9.00))));
        assertThat(model.getProlongation30(), is(closeTo(new BigDecimal(15.00))));
    }

    @Test
    public void shouldCreateAndPersisCreditOnSubmit() throws Exception {
        calculatorService.submit(10, 30, true);
        ArgumentCaptor<Credit> argument = ArgumentCaptor.forClass(Credit.class);
        verify(creditRepository).persist(argument.capture());
        Credit credit = argument.getValue();
        assertThat(credit.getNumberOfDays(), is(10));
        assertThat(credit.getAmount(), is(30));
        assertThat(credit.isFirst(), is(true));
        assertThat(credit.getCommission(), is(notNullValue()));
    }
}

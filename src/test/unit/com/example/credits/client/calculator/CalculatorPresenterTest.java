package com.example.credits.client.calculator;

import com.example.credits.client.common.AsyncCallbackWithFailureHandling;
import com.example.credits.shared.services.CalculatorServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("unchecked")
public class CalculatorPresenterTest {

    private CalculatorPresenter presenter;
    @Mock private CalculatorServiceAsync calculatorService;
    @Mock private HandlerManager eventBus;
    @Mock private CalculatorPresenter.Display view;
    @Mock private HasValue<Integer> amount;
    @Mock private HasValue<Integer> days;
    @Mock private HasValue<Boolean> isFirst;

    @Before
    public void setUp() throws Exception {
        GWTMockUtilities.disarm();
        initMocks(this);
        presenter = new CalculatorPresenter(calculatorService, eventBus, view);

        when(view.getAmount()).thenReturn(amount);
        when(view.getDays()).thenReturn(days);
        when(view.getFirst()).thenReturn(isFirst);
    }

    @Test
    public void shouldSetAmountToMaxValueForFirstCreditWhenFirstIsSelected() throws Exception {
        ValueChangeEvent event = mock(ValueChangeEvent.class);
        when(event.getSource()).thenReturn(isFirst);
        when(isFirst.getValue()).thenReturn(Boolean.TRUE);
        when(amount.getValue()).thenReturn(CalculatorPresenter.MAX_AMOUNT_FOR_FIRST + 100);
        presenter.onValueChange(event);
        verify(amount).setValue(CalculatorPresenter.MAX_AMOUNT_FOR_FIRST);
    }

    @Test
    public void shouldSetIsFirstToFalseWhenAmountBecomesLargerThanMaxForFirst() throws Exception {
        ValueChangeEvent event = mock(ValueChangeEvent.class);
        when(event.getSource()).thenReturn(amount);
        when(amount.getValue()).thenReturn(CalculatorPresenter.MAX_AMOUNT_FOR_FIRST + 100);
        when(isFirst.getValue()).thenReturn(Boolean.TRUE);
        presenter.onValueChange(event);
        verify(isFirst).setValue(Boolean.FALSE);
    }

    @Test
    public void shouldSubmitRequestAndDisableButtonWhenSubmitButtonClicked() throws Exception {
        Button submitButton = mock(Button.class);
        when(view.getSubmitButton()).thenReturn(submitButton);
        ClickEvent event = mock(ClickEvent.class);
        when(event.getSource()).thenReturn(submitButton);
        when(days.getValue()).thenReturn(10);
        when(amount.getValue()).thenReturn(100);
        when(isFirst.getValue()).thenReturn(Boolean.TRUE);
        presenter.onClick(event);
        verify(submitButton).setEnabled(false);
        verify(calculatorService).submit(eq(10), eq(100), eq(Boolean.TRUE), any(AsyncCallbackWithFailureHandling.class));
    }
}

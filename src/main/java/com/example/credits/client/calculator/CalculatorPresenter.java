package com.example.credits.client.calculator;

import com.example.credits.client.common.AsyncCallbackWithFailureHandling;
import com.example.credits.client.common.Presenter;
import com.example.credits.client.credits.ShowCreditsEvent;
import com.example.credits.shared.domain.Credit;
import com.example.credits.shared.services.CreditServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CalculatorPresenter implements Presenter, ClickHandler, ValueChangeHandler<Integer> {

    public interface Display {
        HasClickHandlers getSubmitButton();
        HasValue<Integer> getDays();
        HasValue<Integer> getAmount();
        Widget asWidget();
        Label getTotal();
    }

    private CreditServiceAsync creditService;
    private HandlerManager eventBus;
    private Display view;
    private CalculateTimer calculateTimer;

    public CalculatorPresenter(CreditServiceAsync creditService,
                               HandlerManager eventBus, Display view) {
        this.creditService = creditService;
        this.eventBus = eventBus;
        this.view = view;
        calculateTimer = new CalculateTimer(creditService, view);
    }

    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(view.asWidget());
    }

    private void bind() {
        view.getSubmitButton().addClickHandler(this);
        view.getAmount().addValueChangeHandler(this);
        view.getDays().addValueChangeHandler(this);
    }

    public void onClick(ClickEvent event) {
        if (view.getSubmitButton().equals(event.getSource())) {
            Integer amount = view.getAmount().getValue();
            Integer days = view.getDays().getValue();
            Credit credit = new Credit();
            credit.setAmount(amount);
            credit.setNumberOfDays(days);
            creditService.save(credit, new AsyncCallbackWithFailureHandling<Void>() {
                public void onSuccess(Void result) {
                    eventBus.fireEvent(new ShowCreditsEvent());
                }
            });
        }
    }

    public void onValueChange(ValueChangeEvent<Integer> integerValueChangeEvent) {
        calculateTimer.cancel();
        calculateTimer.schedule(500);
    }

    private static class CalculateTimer extends Timer {
        private CreditServiceAsync creditService;
        private Display view;

        private CalculateTimer(CreditServiceAsync creditService, Display view) {
            this.creditService = creditService;
            this.view = view;
        }

        @Override
        public void run() {
            creditService.calculate(view.getDays().getValue(), view.getAmount().getValue(), new AsyncCallbackWithFailureHandling<Credit>() {
                public void onSuccess(Credit result) {
                    NumberFormat currencyFormat = NumberFormat.getCurrencyFormat("LVL");
                    String value = currencyFormat.format(result.getTotal().doubleValue());
                    view.getTotal().setText(value);
                }
            });
        }
    }
}

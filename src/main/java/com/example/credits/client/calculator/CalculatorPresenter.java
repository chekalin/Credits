package com.example.credits.client.calculator;

import com.example.credits.client.common.AsyncCallbackWithFailureHandling;
import com.example.credits.client.common.Presenter;
import com.example.credits.client.credits.ShowCreditsEvent;
import com.example.credits.shared.domain.Credit;
import com.example.credits.shared.services.CalculatorService;
import com.example.credits.shared.services.CalculatorServiceAsync;
import com.example.credits.shared.services.CreditServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class CalculatorPresenter implements Presenter, ClickHandler, ValueChangeHandler<Integer> {

    public interface Display {
        HasClickHandlers getSubmitButton();
        HasValue<Integer> getDays();
        HasValue<Integer> getAmount();
        HasValue<Boolean> getFirst();
        Widget asWidget();
        HasText getAmountLabel();
        HasText getComissionLabel();
        HasText getTotalLabel();
        HasText getDeadlineLabel();
        HasText getProlongation7Label();
        HasText getProlongation14Label();
        HasText getProlongation30Label();
    }

    private CreditServiceAsync creditService;
    private CalculatorServiceAsync calculatorService = GWT.create(CalculatorService.class);
    private HandlerManager eventBus;
    private Display view;
    private CalculateTimer calculateTimer;

    public CalculatorPresenter(CreditServiceAsync creditService,
                               HandlerManager eventBus, Display view) {
        this.creditService = creditService;
        this.eventBus = eventBus;
        this.view = view;
        calculateTimer = new CalculateTimer(calculatorService, view);
    }

    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(view.asWidget());
        calculatorService.load(new AsyncCallbackWithFailureHandling<CalculatorModel>() {
            @Override
            public void onSuccess(CalculatorModel result) {
                updateView(result);
            }
        });
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
            calculateTimer.cancel();
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

    private void updateView(CalculatorModel model) {
        view.getFirst().setValue(model.getFirst());
        view.getDays().setValue(model.getNumberOfDays());
        view.getAmount().setValue(model.getAmount());
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
        String deadlineLabel = "Līdz " + dateFormat.format(model.getDeadline()) + " Tev jāatmaksā:";
        view.getDeadlineLabel().setText(deadlineLabel);
        NumberFormat currencyFormat = NumberFormat.getCurrencyFormat("LVL");
        view.getAmountLabel().setText(currencyFormat.format(model.getAmount()));
        view.getComissionLabel().setText(currencyFormat.format(model.getComission()));
        view.getTotalLabel().setText(currencyFormat.format(model.calculateTotal()));
        view.getProlongation7Label().setText(currencyFormat.format(model.getProlongation7()));
        view.getProlongation14Label().setText(currencyFormat.format(model.getProlongation14()));
        view.getProlongation30Label().setText(currencyFormat.format(model.getProlongation30()));
    }

    private class CalculateTimer extends Timer {
        private CalculatorServiceAsync calculatorService;
        private Display view;

        private CalculateTimer(CalculatorServiceAsync calculatorService, Display view) {
            this.calculatorService = calculatorService;
            this.view = view;
        }

        @Override
        public void run() {
            Boolean isFirst = view.getFirst().getValue();
            Integer numberOfDays = view.getDays().getValue();
            Integer amount = view.getAmount().getValue();
            calculatorService.calculate(numberOfDays, amount, isFirst, new AsyncCallbackWithFailureHandling<CalculatorModel>() {
                @Override
                public void onSuccess(CalculatorModel result) {
                    updateView(result);
                }
            });
        }
    }

}

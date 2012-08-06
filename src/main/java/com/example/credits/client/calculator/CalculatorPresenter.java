package com.example.credits.client.calculator;

import com.example.credits.client.common.AsyncCallbackWithFailureHandling;
import com.example.credits.client.common.Formats;
import com.example.credits.client.common.Presenter;
import com.example.credits.client.credits.ShowCreditsEvent;
import com.example.credits.shared.services.CalculatorServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

public class CalculatorPresenter implements Presenter, ClickHandler, ValueChangeHandler {

    public static final int MAX_AMOUNT_FOR_FIRST = 200;

    public interface Display {
        Button getSubmitButton();
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

    private CalculatorServiceAsync calculatorService;
    private HandlerManager eventBus;
    private Display view;
    private CalculateTimer calculateTimer = new CalculateTimer();

    public CalculatorPresenter(CalculatorServiceAsync calculatorService, HandlerManager eventBus, Display view) {
        this.calculatorService = calculatorService;
        this.eventBus = eventBus;
        this.view = view;
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

    @SuppressWarnings("unchecked")
    private void bind() {
        view.getSubmitButton().addClickHandler(this);
        view.getAmount().addValueChangeHandler(this);
        view.getDays().addValueChangeHandler(this);
        view.getFirst().addValueChangeHandler(this);
    }

    public void onClick(ClickEvent event) {
        if (view.getSubmitButton().equals(event.getSource())) {
            view.getSubmitButton().setEnabled(false);
            Integer amount = view.getAmount().getValue();
            Integer days = view.getDays().getValue();
            Boolean isFirst = view.getFirst().getValue();
            calculatorService.submit(days, amount, isFirst, new AsyncCallbackWithFailureHandling<Void>() {
                public void onSuccess(Void result) {
                    eventBus.fireEvent(new ShowCreditsEvent());
                }

                @Override
                public void onFailure(Throwable caught) {
                    super.onFailure(caught);
                    view.getSubmitButton().setEnabled(true);
                }
            });
        }
    }

    public void onValueChange(ValueChangeEvent event) {
        if (event.getSource().equals(view.getFirst())
                && Boolean.TRUE.equals(view.getFirst().getValue())
                && view.getAmount().getValue() > MAX_AMOUNT_FOR_FIRST) {
            view.getAmount().setValue(MAX_AMOUNT_FOR_FIRST);
        } else if (event.getSource().equals(view.getAmount()) && view.getAmount().getValue() > 200 && view.getFirst().getValue()) {
            view.getFirst().setValue(Boolean.FALSE);
        } else {
            calculateTimer.cancel();
            calculateTimer.schedule(500);
        }
    }

    private void calculate() {
        Integer amount = view.getAmount().getValue();
        Integer numberOfDays = view.getDays().getValue();
        Boolean first = view.getFirst().getValue();
        calculatorService.calculate(numberOfDays, amount, first, new AsyncCallbackWithFailureHandling<CalculatorModel>() {
            @Override
            public void onSuccess(CalculatorModel result) {
                updateView(result);
            }
        });
    }

    private void updateView(CalculatorModel model) {
        view.getFirst().setValue(model.getFirst());
        view.getDays().setValue(model.getNumberOfDays());
        view.getAmount().setValue(model.getAmount());
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(Formats.DATE_FORMAT);
        String deadlineLabel = "Līdz " + dateFormat.format(model.getDeadline()) + " Tev jāatmaksā:";
        view.getDeadlineLabel().setText(deadlineLabel);
        NumberFormat currencyFormat = NumberFormat.getFormat(Formats.CURRENCY_FORMAT);
        view.getAmountLabel().setText(currencyFormat.format(model.getAmount()));
        view.getComissionLabel().setText(currencyFormat.format(model.getComission()));
        view.getTotalLabel().setText(currencyFormat.format(model.calculateTotal()));
        view.getProlongation7Label().setText(currencyFormat.format(model.getProlongation7()));
        view.getProlongation14Label().setText(currencyFormat.format(model.getProlongation14()));
        view.getProlongation30Label().setText(currencyFormat.format(model.getProlongation30()));
    }
    private class CalculateTimer extends Timer {
        @Override
        public void run() {
            calculate();
        }
    }

}

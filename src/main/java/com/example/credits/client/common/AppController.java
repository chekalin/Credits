package com.example.credits.client.common;

import com.example.credits.client.calculator.CalculatorPresenter;
import com.example.credits.client.calculator.CalculatorView;
import com.example.credits.client.calculator.ShowCalculatorEvent;
import com.example.credits.client.calculator.ShowCalculatorEventHandler;
import com.example.credits.client.credits.CreditsPresenter;
import com.example.credits.client.credits.CreditsView;
import com.example.credits.client.credits.ShowCreditsEvent;
import com.example.credits.client.credits.ShowCreditsEventHandler;
import com.example.credits.shared.services.CreditServiceAsync;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {

    public static final String NAVIGATION_LIST = "list";
    public static final String NAVIGATION_CALCULATOR = "calculator";
    private CreditServiceAsync creditService;
    private HandlerManager eventBus;
    private HasWidgets container;

    public AppController(CreditServiceAsync creditService, HandlerManager eventBus) {
        this.creditService = creditService;
        this.eventBus = eventBus;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);
        eventBus.addHandler(ShowCreditsEvent.TYPE, new ShowCreditsEventHandler() {
            public void onShowCredits(ShowCreditsEvent showCreditsEvent) {
                History.newItem(NAVIGATION_LIST);
            }
        });
        eventBus.addHandler(ShowCalculatorEvent.TYPE, new ShowCalculatorEventHandler() {
            public void onShowCalculator(ShowCalculatorEvent showCalculatorEvent) {
                History.newItem(NAVIGATION_CALCULATOR);
            }
        });
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token != null) {
            Presenter presenter = null;
            if (token.equals(NAVIGATION_CALCULATOR)) {
                presenter = new CalculatorPresenter(creditService, eventBus, new CalculatorView());
            } else if (token.equals(NAVIGATION_LIST)) {
                presenter = new CreditsPresenter(creditService, eventBus, new CreditsView());
            }
            if (presenter != null) {
                presenter.go(container);
            }
        }
    }

    public void go(HasWidgets container) {
        this.container = container;
        if ("".equals(History.getToken())) {
            History.newItem(NAVIGATION_CALCULATOR);
        } else {
            History.fireCurrentHistoryState();
        }
    }

}

/*
 * @(#)CreditsPresenter.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.client.credits;

import com.example.credits.client.calculator.ShowCalculatorEvent;
import com.example.credits.client.common.AsyncCallbackWithFailureHandling;
import com.example.credits.client.common.Presenter;
import com.example.credits.shared.domain.Credit;
import com.example.credits.shared.services.CreditServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

public class CreditsPresenter implements Presenter, ClickHandler {
    private CreditServiceAsync creditService;
    private HandlerManager eventBus;
    private Display view;

    public CreditsPresenter(CreditServiceAsync creditService, HandlerManager eventBus, Display view) {
        this.creditService = creditService;
        this.eventBus = eventBus;
        this.view = view;
    }

    public void onClick(ClickEvent event) {
        if (event.getSource().equals(view.getBackButton())) {
            eventBus.fireEvent(new ShowCalculatorEvent());
        }
    }

    public interface Display {
        Widget asWidget();
        TextArea getTextArea();
        Button getBackButton();
    }

    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());

        creditService.findAllCredits(new AsyncCallbackWithFailureHandling<List<Credit>>() {
            public void onSuccess(List<Credit> result) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Credit credit : result) {
                    stringBuilder.append(credit.toString()).append("\n");
                }
                view.getTextArea().setValue(stringBuilder.toString());
            }
        });

        view.getBackButton().addClickHandler(this);
    }
}

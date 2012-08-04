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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.*;

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
        FlexTable getFlexTable();
        Button getBackButton();
    }

    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());

        creditService.findAllCredits(new AsyncCallbackWithFailureHandling<List<Credit>>() {
            public void onSuccess(List<Credit> result) {
                int row = 1;
                NumberFormat currencyFormat = NumberFormat.getCurrencyFormat("LVL");
                DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
                FlexTable flexTable = view.getFlexTable();
                for (Credit credit : result) {
                    flexTable.setHTML(row, 0, String.valueOf(credit.getCreditId()));
                    flexTable.setHTML(row, 1, currencyFormat.format(credit.getAmount()));
                    flexTable.setHTML(row, 2, String.valueOf(credit.getNumberOfDays()));
                    flexTable.setHTML(row, 3, currencyFormat.format(credit.getCommission()));
                    flexTable.setHTML(row, 4, dateFormat.format(credit.getCreated()));
                    row++;
                }
            }
        });

        view.getBackButton().addClickHandler(this);
    }
}

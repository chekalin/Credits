package com.example.credits.client;

import com.example.credits.client.common.AppController;
import com.example.credits.shared.services.CalculatorService;
import com.example.credits.shared.services.CalculatorServiceAsync;
import com.example.credits.shared.services.CreditService;
import com.example.credits.shared.services.CreditServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class Credits implements EntryPoint {
	public void onModuleLoad() {
		CreditServiceAsync creditService = GWT.create(CreditService.class);
		CalculatorServiceAsync calculatorService = GWT.create(CalculatorService.class);
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(calculatorService, creditService, eventBus);
	    appViewer.go(RootPanel.get("mainContent"));
	}
}

package com.example.credits.shared.services;

import com.example.credits.shared.domain.Credit;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface CreditServiceAsync {

	void calculate(int days, int amount, AsyncCallback<Credit> callback);

	void findAllCredits(AsyncCallback<List<Credit>> callback);

    void save(Credit credit, AsyncCallback<Void> async);
}

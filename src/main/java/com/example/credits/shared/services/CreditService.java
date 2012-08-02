package com.example.credits.shared.services;

import com.example.credits.shared.domain.Credit;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/creditService")
public interface CreditService extends RemoteService {
	
	public Credit calculate(int days, int amount);

	public void save(Credit credit);

	public List<Credit> findAllCredits();
}

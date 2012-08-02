package com.example.credits.server.application;

import com.example.credits.server.persistence.CreditRepository;
import com.example.credits.shared.domain.Credit;
import com.example.credits.shared.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("creditService")
public class CreditServiceImpl implements CreditService {
	
	@Autowired private CreditRepository creditRepository;
	
	public Credit calculate(Credit credit) {
		return credit;
	}

    public Credit calculate(int days, int amount) {
        Credit credit = new Credit();
        credit.setNumberOfDays(days);
        credit.setAmount(amount);
        credit.setComission(new BigDecimal(3.14));
        return credit;
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void save(Credit credit) {
		creditRepository.persist(credit);
	}

	public List<Credit> findAllCredits() {
		return creditRepository.findAll();
	}
	

}

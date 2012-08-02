package com.example.credits.server.persistence;

import com.example.credits.shared.domain.Credit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CreditRepositoryImpl implements CreditRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void persist(Credit credit) {
		entityManager.persist(credit);
	}

	@SuppressWarnings("unchecked")
	public List<Credit> findAll() {
		return entityManager.createQuery("select c from Credit c order by c.created").getResultList();
	}
}

/*
 * @(#)IntegrationRepositoryTest.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.server.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public abstract class IntegrationRepositoryTestBase extends AbstractJUnit4SpringContextTests {

    @PersistenceContext protected EntityManager entityManager;
    @Autowired private PlatformTransactionManager transactionManager;

    protected void executeInTransaction(final UnitOfWork unitOfWork) {
        new TransactionTemplate(transactionManager).execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
                unitOfWork.execute();
                return null;
            }
        });
    }
}

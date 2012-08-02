/*
 * @(#)CreditRepositoryImplTest.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.server.persistence;

import com.example.credits.shared.domain.Credit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static com.example.credits.server.persistence.Matchers.sameDay;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreditRepositoryImplTest extends IntegrationRepositoryTestBase {

    @Autowired CreditRepository creditRepository;

    @Test
    public void testName() throws Exception {
        final Credit beforePersist = new Credit();
        beforePersist.setAmount(1);
        beforePersist.setNumberOfDays(30);
        beforePersist.setComission(new BigDecimal(100));
        executeInTransaction(new UnitOfWork() {
            public void execute() {
                creditRepository.persist(beforePersist);
            }
        });

        Credit afterPersist = entityManager.find(Credit.class, beforePersist.getCreditId());

        assertThat(afterPersist.getAmount(), is(beforePersist.getAmount()));
        assertThat(afterPersist.getNumberOfDays(), is(beforePersist.getNumberOfDays()));
        assertThat(afterPersist.getComission(), is(beforePersist.getComission()));
        assertThat(afterPersist.getCreated(), is(sameDay(new Date())));
    }

}

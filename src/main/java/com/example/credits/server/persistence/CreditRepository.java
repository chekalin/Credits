/*
 * @(#)CreditRepository.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.server.persistence;

import com.example.credits.shared.domain.Credit;

import java.util.List;

public interface CreditRepository {
    void persist(Credit credit);

    @SuppressWarnings("unchecked")
    List<Credit> findAll();
}

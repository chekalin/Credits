/*
 * @(#)UnitOfWork.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.server.persistence;

public interface UnitOfWork {
    void execute();
}

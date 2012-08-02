/*
 * @(#)ShowCalculatorEvent.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.client.calculator;

import com.google.gwt.event.shared.GwtEvent;

public class ShowCalculatorEvent extends GwtEvent<ShowCalculatorEventHandler> {

    public static Type<ShowCalculatorEventHandler> TYPE = new Type<ShowCalculatorEventHandler>();

    @Override
    public Type<ShowCalculatorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowCalculatorEventHandler handler) {
        handler.onShowCalculator(this);
    }
}

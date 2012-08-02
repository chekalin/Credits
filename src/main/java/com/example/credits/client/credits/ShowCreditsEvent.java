/*
 * @(#)ShowCreditsEvent.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.client.credits;

import com.google.gwt.event.shared.GwtEvent;

public class ShowCreditsEvent extends GwtEvent<ShowCreditsEventHandler> {

    public static Type<ShowCreditsEventHandler> TYPE = new Type<ShowCreditsEventHandler>();

    @Override
    public Type<ShowCreditsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowCreditsEventHandler handler) {
        handler.onShowCredits(this);
    }
}

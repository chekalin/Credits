/*
 * @(#)CreditsView.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.client.credits;

import com.google.gwt.user.client.ui.*;

public class CreditsView extends Composite implements CreditsPresenter.Display {

    private FlexTable flexTable = new FlexTable();
    private Button backButton = new Button("BACK");

    public CreditsView() {
        VerticalPanel panel = new VerticalPanel();
        initWidget(panel);
        flexTable.setWidget(0, 0, new Label("ID"));
        flexTable.setWidget(0, 1, new Label("Amount"));
        flexTable.setWidget(0, 2, new Label("Days"));
        flexTable.setWidget(0, 3, new Label("Commission"));
        flexTable.setWidget(0, 4, new Label("Created"));
        panel.add(flexTable);
        panel.add(backButton);
    }

    public FlexTable getFlexTable() {
        return flexTable;
    }

    public Button getBackButton() {
        return backButton;
    }
}

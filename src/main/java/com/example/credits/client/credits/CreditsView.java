/*
 * @(#)CreditsView.java
 *
 * Copyright Swiss Reinsurance Company, Mythenquai 50/60, CH 8022 Zurich. All rights reserved.
 */
package com.example.credits.client.credits;

import com.google.gwt.user.client.ui.*;

public class CreditsView extends Composite implements CreditsPresenter.Display {

    private TextArea textArea = new TextArea();
    private Button backButton = new Button("BACK");

    public CreditsView() {
        VerticalPanel panel = new VerticalPanel();
        initWidget(panel);
        textArea = new TextArea();
        panel.add(textArea);
        panel.add(backButton);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Button getBackButton() {
        return backButton;
    }
}

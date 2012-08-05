package com.example.credits.client.calculator;

import com.example.credits.client.calculator.slider.SliderWidget;
import com.google.gwt.user.client.ui.*;

public class CalculatorView extends Composite implements CalculatorPresenter.Display {
	
    private Button submitButton;
    private SliderWidget daysSlider = new SliderWidget("days", 1, 30, 1, "Uz cik dienām?", "Līdz 30 dienām");
    private SliderWidget amountSlider = new SliderWidget("amount", 1, 350, 1, "Kādu summu vēlies?", "Līdz Ls 350");
    private Label amountLabel = new Label();
    private Label comissionLabel = new Label();
    private Label totalLabel = new Label();
    private final Label deadlineLabel = new Label();
    private Label prolongation7Label = new Label();
    private Label prolongation14Label = new Label();
    private Label prolongation30Label = new Label();
    private IsFirstTimeWidget isFirstTime = new IsFirstTimeWidget();

    public CalculatorView() {
		VerticalPanel panel = new VerticalPanel();
	    initWidget(panel);
        panel.add(isFirstTime);
        HorizontalPanel creditInfo = new HorizontalPanel();
        creditInfo.add(sliders());
        creditInfo.add(creditInfoTable());
        creditInfo.setStyleName("credit-info-panel");
        panel.add(creditInfo);

    }

    private VerticalPanel sliders() {
        VerticalPanel sliders = new VerticalPanel();
        sliders.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        sliders.add(amountSlider);
        sliders.add(daysSlider);
        submitButton = new Button("Saņemt ātro kredītu");
        submitButton.setStyleName("credit-submit-button");
        sliders.add(submitButton);
        return sliders;
    }

    private FlexTable creditInfoTable() {
        FlexTable creditInfo = new FlexTable();
        creditInfo.getFlexCellFormatter().setColSpan(0, 0, 2);
        creditInfo.setWidget(0, 0, deadlineLabel);
        deadlineLabel.setStyleName("credit-info-header");
        creditInfo.setWidget(1, 0, new Label("Kredīts:"));
        creditInfo.setWidget(1, 1, amountLabel);
        creditInfo.setWidget(2, 0, new Label("Komisija:"));
        creditInfo.setWidget(2, 1, comissionLabel);
        creditInfo.setWidget(3, 0, new Label("Kopā:"));
        creditInfo.setWidget(3, 1, totalLabel);
        Label prolongationLabel = new Label("Varēsi arī pagarināt termiņu par:");
        prolongationLabel.setStyleName("credit-info-header");
        creditInfo.setWidget(4, 0, prolongationLabel);
        creditInfo.getFlexCellFormatter().setColSpan(4, 0, 2);
        creditInfo.setWidget(5, 0, new Label("7 dienām"));
        creditInfo.setWidget(5, 1, prolongation7Label);
        creditInfo.setWidget(6, 0, new Label("14 dienām"));
        creditInfo.setWidget(6, 1, prolongation14Label);
        creditInfo.setWidget(7, 0, new Label("30 dienām"));
        creditInfo.setWidget(7, 1, prolongation30Label);
        amountLabel.setStyleName("credit-info-number");
        comissionLabel.setStyleName("credit-info-number");
        totalLabel.setStyleName("credit-info-number");
        prolongation7Label.setStyleName("credit-info-number");
        prolongation14Label.setStyleName("credit-info-number");
        prolongation30Label.setStyleName("credit-info-number");
        creditInfo.setStyleName("credit-info-table");
        return creditInfo;
    }

    public Button getSubmitButton() {
		return submitButton;
	}

    public HasValue<Integer> getDays() {
        return daysSlider;
    }

    public HasValue<Integer> getAmount() {
        return amountSlider;
    }

    @Override
    public HasValue<Boolean> getFirst() {
        return isFirstTime;
    }

    @Override
    public HasText getAmountLabel() {
        return amountLabel;
    }

    @Override
    public HasText getComissionLabel() {
        return comissionLabel;
    }

    @Override
    public HasText getTotalLabel() {
        return totalLabel;
    }

    @Override
    public HasText getDeadlineLabel() {
        return deadlineLabel;
    }

    @Override
    public HasText getProlongation7Label() {
        return prolongation7Label;
    }

    @Override
    public HasText getProlongation14Label() {
        return prolongation14Label;
    }

    @Override
    public HasText getProlongation30Label() {
        return prolongation30Label;
    }
}

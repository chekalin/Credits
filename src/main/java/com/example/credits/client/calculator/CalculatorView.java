package com.example.credits.client.calculator;

import com.example.credits.client.calculator.slider.SliderWidget;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;

public class CalculatorView extends Composite implements CalculatorPresenter.Display {
	
    private Button submitButton;
    private SliderWidget daysSlider = new SliderWidget("days", 1, 30, 1);
    private SliderWidget amountSlider = new SliderWidget("amount", 1, 350, 1);
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
        HorizontalPanel creditInfo = new HorizontalPanel();
        creditInfo.add(sliders());
        creditInfo.add(creditInfoTable());
        panel.add(isFirstTime);
        panel.add(creditInfo);
	}

    private VerticalPanel sliders() {
        VerticalPanel sliders = new VerticalPanel();
        sliders.add(daysSlider);
        sliders.add(amountSlider);
        submitButton = new Button("Submit");
        sliders.add(submitButton);
        return sliders;
    }

    private FlexTable creditInfoTable() {
        FlexTable creditInfo = new FlexTable();
        creditInfo.getFlexCellFormatter().setColSpan(0, 0, 2);
        creditInfo.setWidget(0, 0, deadlineLabel);
        creditInfo.setWidget(1, 0, new Label("Kredīts:"));
        creditInfo.setWidget(1, 1, amountLabel);
        creditInfo.setWidget(2, 0, new Label("Komisija:"));
        creditInfo.setWidget(2, 1, comissionLabel);
        creditInfo.setWidget(3, 0, new Label("Kopā:"));
        creditInfo.setWidget(3, 1, totalLabel);
        creditInfo.setWidget(4, 0, new Label("Varēsi arī pagarināt termiņu par:"));
        creditInfo.getFlexCellFormatter().setColSpan(4, 0, 2);
        creditInfo.setWidget(5, 0, new Label("7 dienām"));
        creditInfo.setWidget(5, 1, prolongation7Label);
        creditInfo.setWidget(6, 0, new Label("14 dienām"));
        creditInfo.setWidget(6, 1, prolongation14Label);
        creditInfo.setWidget(7, 0, new Label("30 dienām"));
        creditInfo.setWidget(7, 1, prolongation30Label);
        return creditInfo;
    }

    public HasClickHandlers getSubmitButton() {
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

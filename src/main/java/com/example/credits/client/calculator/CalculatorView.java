package com.example.credits.client.calculator;

import com.example.credits.client.calculator.slider.SliderWidget;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;

public class CalculatorView extends Composite implements CalculatorPresenter.Display {
	
    private Button submitButton;
    private SliderWidget daysSlider = new SliderWidget("days", 1, 30, 1);
    private SliderWidget amountSlider = new SliderWidget("amount", 1, 200, 1);
    private Label sum;

    public CalculatorView() {
		HorizontalPanel panel = new HorizontalPanel();
	    initWidget(panel);
	    panel.setWidth("100%");
	    panel.setWidth("18em");
	    VerticalPanel sliders = new VerticalPanel();
        sliders.add(daysSlider);
	    sliders.add(amountSlider);
	    submitButton = new Button("Submit");
	    sliders.add(submitButton);
	    panel.add(sliders);
        sum = new Label();
        panel.add(sum);
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

    public Label getTotal() {
        return sum;
    }
}

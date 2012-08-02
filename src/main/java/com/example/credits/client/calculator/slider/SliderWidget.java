package com.example.credits.client.calculator.slider;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import org.spiffyui.client.widgets.slider.Slider;
import org.spiffyui.client.widgets.slider.SliderOption;

public class SliderWidget extends Composite implements HasChangeHandlers, SliderWidgetPresenter.Display {

	private final Slider slider;
	private final IntegerBox input;
    private int value;
    private Button minusButton;
    private Button plusButton;

    public SliderWidget(String sliderName, final int minValue, final int maxValue, int defaultValue) {
        this.value = defaultValue;
		HorizontalPanel panel = new HorizontalPanel();
        initWidget(panel);
        slider = createSlider(sliderName, minValue, maxValue, defaultValue);
        input = createInputBox(defaultValue);
        minusButton = new Button("-");
        panel.add(minusButton);
        panel.add(slider);
        plusButton = new Button("+");
        panel.add(plusButton);
        panel.add(input);
        final SliderWidgetPresenter presenter = new SliderWidgetPresenter(this, minValue, maxValue);
        presenter.bind();
    }

    private IntegerBox createInputBox(int defaultValue) {
        IntegerBox input = new IntegerBox();
        input.setHeight("20px");
        input.setWidth("33px");
        input.setValue(defaultValue);
        return input;
    }

    private Slider createSlider(String sliderName, int minValue, int maxValue, int defaultValue) {
        Slider slider = new Slider(sliderName, minValue, maxValue, defaultValue);
        slider.setWidth("200px");
        slider.setStringOption(SliderOption.RANGE, "max");
        return slider;
    }

    public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
        setValue(value, true);
    }

	public void setValue(Integer value, boolean fireEvents) {
        this.value = value;
        if (fireEvents) {
            ValueChangeEvent.fire(this, value);
        }
	}

    public Slider getSlider() {
        return slider;
    }

    public IntegerBox getInput() {
        return input;
    }

    public Button getMinusButton() {
        return minusButton;
    }

    public Button getPlusButton() {
        return plusButton;
    }

    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    public HandlerRegistration addChangeHandler(ChangeHandler handler) {
        return addHandler(handler, ChangeEvent.getType());
    }
}

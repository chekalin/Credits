package com.example.credits.client.calculator.slider;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import org.spiffyui.client.widgets.slider.Slider;
import org.spiffyui.client.widgets.slider.SliderOption;

public class SliderWidget extends Composite implements HasChangeHandlers, SliderWidgetPresenter.Display {

	private final Slider slider;
	private final IntegerBox input;
    private int value;
    private Button minusButton;
    private Button plusButton;

    public SliderWidget(String sliderName, final int minValue, final int maxValue, int defaultValue, String mainLabel, String subLabel) {
        this.value = defaultValue;
		HorizontalPanel panel = new HorizontalPanel();
        initWidget(panel);
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        panel.add(createLabels(mainLabel, subLabel));
        slider = createSlider(sliderName, minValue, maxValue, defaultValue);
        input = createInputBox(defaultValue);
        minusButton = createButton("-");
        panel.add(minusButton);
        panel.add(slider);
        plusButton = createButton("+");
        panel.add(plusButton);
        panel.add(input);
        final SliderWidgetPresenter presenter = new SliderWidgetPresenter(this, minValue, maxValue);
        presenter.bind();
    }

    private Button createButton(String value) {
        Button button = new Button(value);
        button.setStyleName("slider-button");
        return button;
    }

    private VerticalPanel createLabels(String mainText, String subText) {
        VerticalPanel labels = new VerticalPanel();
        labels.setStyleName("slider-labels");
        Label mainLabel = new Label(mainText);
        mainLabel.setStyleName("slider-main-label");
        labels.add(mainLabel);
        Label subLabel = new Label(subText);
        subLabel.setStyleName("slider-sub-label");
        labels.add(subLabel);
        return labels;
    }

    private IntegerBox createInputBox(int defaultValue) {
        IntegerBox input = new IntegerBox();
        input.setValue(defaultValue);
        input.setStyleName("slider-input");
        return input;
    }

    private Slider createSlider(String sliderName, int minValue, int maxValue, int defaultValue) {
        Slider slider = new Slider(sliderName, minValue, maxValue, defaultValue);
        slider.setStringOption(SliderOption.RANGE, "max");
        slider.addStyleName("slider-bar");
        return slider;
    }

    public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
        setValue(value, true);
    }

	public void setValue(Integer newValue, boolean fireEvents) {
        Integer oldValue = this.value;
        this.value = newValue;
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
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

package com.example.credits.client.calculator.slider;

import com.google.gwt.event.dom.client.*;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IntegerBox;
import org.spiffyui.client.widgets.slider.Slider;
import org.spiffyui.client.widgets.slider.SliderEvent;
import org.spiffyui.client.widgets.slider.SliderListener;

class SliderWidgetPresenter implements KeyPressHandler, ValueChangeHandler<Integer>, SliderListener, ClickHandler {

    public interface Display extends HasValue<Integer> {
        Slider getSlider();
        IntegerBox getInput();
        Button getMinusButton();
        Button getPlusButton();
    }

    private final Display view;
    private final int minValue;
    private final int maxValue;

    public SliderWidgetPresenter(Display view, int minValue, int maxValue) {
        this.view = view;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void bind() {
        view.getInput().addKeyPressHandler(this);
        view.getInput().addValueChangeHandler(this);
        view.getSlider().addListener(this);
        view.getMinusButton().addClickHandler(this);
        view.getPlusButton().addClickHandler(this);
        view.addValueChangeHandler(this);
    }

    public void onKeyPress(KeyPressEvent event) {
        if (event.getSource().equals(view.getInput())) {
            if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                Integer newValue = getCorrectedValueFromInput();
                view.setValue(newValue);
            }
        }
    }

    private Integer getCorrectedValueFromInput() {
        String value = view.getInput().getText();
        Integer newValue;
        if (!value.matches("[0-9]*")) {
            newValue = minValue;
        } else {
            newValue = view.getInput().getValue();
        }
        return correctMinMax(newValue);
    }

    public void onValueChange(ValueChangeEvent<Integer> event) {
        if (event.getSource().equals(view)) {
            Integer newValue = event.getValue();
            view.setValue(newValue, false);
            view.getSlider().setValue(newValue);
            view.getInput().setValue(newValue, false);
        } else if (event.getSource().equals(view.getInput())) {
            view.setValue(getCorrectedValueFromInput());
        }
    }

    public boolean onSlide(SliderEvent e) {
        int newValue = e.getValues()[0];
        view.setValue(newValue);
        return true;
    }

    public void onClick(ClickEvent event) {
        if (event.getSource().equals(view.getPlusButton()) && view.getValue() < maxValue) {
            view.setValue(view.getValue() + 1);
        } else if (event.getSource().equals(view.getMinusButton()) && view.getValue() > minValue) {
            view.setValue(view.getValue() - 1);
        }
    }

    private Integer correctMinMax(Integer newValue) {
        if (newValue < minValue) {
            return minValue;
        } else if (newValue > maxValue) {
            return maxValue;
        } else {
            return newValue;
        }
    }

    public void onStart(SliderEvent e) {
    }

    public void onChange(SliderEvent e) {
    }

    public void onStop(SliderEvent e) {
    }
}

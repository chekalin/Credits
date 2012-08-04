package com.example.credits.client.calculator;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;

public class IsFirstTimeWidget extends Composite implements IsWidget, HasValue<Boolean> {

    private final ToggleButton first = new ToggleButton("Pirmais kredīts");
    private final ToggleButton recurrent = new ToggleButton("Atkārtots kredīts");
    private Boolean value = Boolean.TRUE;

    public IsFirstTimeWidget() {
        HorizontalPanel panel = new HorizontalPanel();
        initWidget(panel);
        panel.add(first);
        panel.add(recurrent);
        first.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                Boolean newValue = event.getValue();
                recurrent.setValue(!newValue, false);
                value = newValue;
            }
        });
        recurrent.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                Boolean newValue = event.getValue();
                first.setValue(!newValue, false);
                value = !newValue;
            }
        });
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        setValue(value, true);
    }

    @Override
    public void setValue(Boolean value, boolean fireEvents) {
        this.value = value;
        first.setValue(value);
        recurrent.setValue(!value);
        if (fireEvents) {
            ValueChangeEvent.fire(this, value);
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }
}

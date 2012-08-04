package com.example.credits.client.calculator;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;

public class IsFirstTimeWidget extends Composite implements IsWidget, HasValue<Boolean> {

    private final ToggleButton first = new ToggleButton("Pirmais kredīts");
    private final ToggleButton recurrent = new ToggleButton("Atkārtots kredīts");
    public IsFirstTimeWidget() {
        HorizontalPanel panel = new HorizontalPanel();
        initWidget(panel);
        panel.add(first);
        panel.add(recurrent);
        first.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if (Boolean.TRUE.equals(event.getValue())) {
                    setValue(Boolean.TRUE);
                }
            }
        });
        recurrent.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if (Boolean.TRUE.equals(event.getValue())) {
                    setValue(Boolean.FALSE);
                }
            }
        });
    }

    private Boolean value = Boolean.TRUE;

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
        Boolean oldValue = this.value;
        this.value = value;
        first.setValue(value);
        recurrent.setValue(!value);
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }
}

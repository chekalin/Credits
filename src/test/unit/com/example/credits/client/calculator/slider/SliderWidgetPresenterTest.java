package com.example.credits.client.calculator.slider;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IntegerBox;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.spiffyui.client.widgets.slider.Slider;
import org.spiffyui.client.widgets.slider.SliderEvent;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("unchecked")
public class SliderWidgetPresenterTest {

    private static final Integer MIN_VALUE = 1;
    private static final Integer MAX_VALUE = 100;
    private static final Integer NEW_VALUE = 30;

    @Mock Slider slider;
    @Mock IntegerBox input;
    @Mock Button plusButton;
    @Mock Button minusButton;

    private SliderWidgetPresenter.Display view = new FakeView();

    private SliderWidgetPresenter presenter = new SliderWidgetPresenter(view, MIN_VALUE, MAX_VALUE);

    @Before
    public void setUp() throws Exception {
        GWTMockUtilities.disarm();
        initMocks(this);
    }

    @Test
    public void shouldUpdateValueOnSliderSlide() throws Exception {
        SliderEvent slideEvent = mock(SliderEvent.class);
        when(slideEvent.getValues()).thenReturn(new int[]{NEW_VALUE});
        presenter.onSlide(slideEvent);
        assertThat(view.getValue(), is(NEW_VALUE));
    }

    @Test
    public void shouldUpdateValueWhenUserPressesEnterOnInput() throws Exception {
        KeyPressEvent event = mock(KeyPressEvent.class);
        when(event.getCharCode()).thenReturn((char) KEY_ENTER);
        when(event.getSource()).thenReturn(input);
        when(input.getText()).thenReturn(NEW_VALUE.toString());
        when(input.getValue()).thenReturn(NEW_VALUE);
        presenter.onKeyPress(event);
        assertThat(view.getValue(), is(NEW_VALUE));
    }

    @Test
    public void shouldSetValueToMinValueWhenInputContainsNonDigitsAndUserPressesEnter() throws Exception {
        KeyPressEvent event = mock(KeyPressEvent.class);
        when(event.getCharCode()).thenReturn((char) KEY_ENTER);
        when(event.getSource()).thenReturn(input);
        when(input.getText()).thenReturn("NaN");
        presenter.onKeyPress(event);
        assertThat(view.getValue(), is(MIN_VALUE));
    }

    @Test
    public void shouldSetValueToMinValueWhenInputValueBelowRange() throws Exception {
        KeyPressEvent event = mock(KeyPressEvent.class);
        when(event.getCharCode()).thenReturn((char) KEY_ENTER);
        when(event.getSource()).thenReturn(input);
        when(input.getText()).thenReturn(String.valueOf(MIN_VALUE - 1));
        when(input.getValue()).thenReturn(MIN_VALUE - 1);
        presenter.onKeyPress(event);
        assertThat(view.getValue(), is(MIN_VALUE));
    }

    @Test
    public void shouldSetValueToMaxValueWhenInputValueAboveRange() throws Exception {
        KeyPressEvent event = mock(KeyPressEvent.class);
        when(event.getCharCode()).thenReturn((char) KEY_ENTER);
        when(event.getSource()).thenReturn(input);
        when(input.getText()).thenReturn(String.valueOf(MAX_VALUE + 1));
        when(input.getValue()).thenReturn(MAX_VALUE + 1);
        presenter.onKeyPress(event);
        assertThat(view.getValue(), is(MAX_VALUE));
    }

    @Test
    public void shouldSetValueToMinValueWhenInputValueChangesToNonDigits() throws Exception {
        ValueChangeEvent<Integer> event = mock(ValueChangeEvent.class);
        when(event.getSource()).thenReturn(input);
        when(input.getText()).thenReturn("NaN");
        presenter.onValueChange(event);
        assertThat(view.getValue(), is(MIN_VALUE));
    }

    @Test
    public void shouldIncrementValuesWhenPlusButtonPressed() throws Exception {
        ClickEvent event = mock(ClickEvent.class);
        view.setValue(NEW_VALUE);
        when(event.getSource()).thenReturn(plusButton);
        presenter.onClick(event);
        assertThat(view.getValue(), is(NEW_VALUE + 1));
    }

    @Test
    public void shouldNotIncrementValuesWhenPlusButtonPressedAndValueIsMax() throws Exception {
        ClickEvent event = mock(ClickEvent.class);
        view.setValue(MAX_VALUE);
        when(event.getSource()).thenReturn(plusButton);
        presenter.onClick(event);
        assertThat(view.getValue(), is(MAX_VALUE));
    }

    @Test
    public void shouldNotDecrementValuesWhenMinusButtonPressedAndValueIsMin() throws Exception {
        ClickEvent event = mock(ClickEvent.class);
        view.setValue(MIN_VALUE);
        when(event.getSource()).thenReturn(minusButton);
        presenter.onClick(event);
        assertThat(view.getValue(), is(MIN_VALUE));
    }

    @Test
    public void shouldDecrementValuesWhenMinusButtonPressed() throws Exception {
        ClickEvent event = mock(ClickEvent.class);
        view.setValue(NEW_VALUE);
        when(event.getSource()).thenReturn(minusButton);
        presenter.onClick(event);
        assertThat(view.getValue(), is(NEW_VALUE - 1));
    }

    @Test
    public void shouldUpdateSliderAndInputWhenValueChanges() throws Exception {
        ValueChangeEvent<Integer> event = mock(ValueChangeEvent.class);
        when(event.getSource()).thenReturn(view);
        when(event.getValue()).thenReturn(NEW_VALUE);
        presenter.onValueChange(event);
        verify(slider).setValue(NEW_VALUE);
        verify(input).setValue(NEW_VALUE, false);
    }

    @Test
    public void shouldBindItselfToAllWidgets() {
        presenter.bind();
        verify(input).addKeyPressHandler(presenter);
        verify(input).addValueChangeHandler(presenter);
        verify(slider).addListener(presenter);
        verify(plusButton).addClickHandler(presenter);
        verify(minusButton).addClickHandler(presenter);
    }

    private class FakeView implements SliderWidgetPresenter.Display {
        public Integer value;

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

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public void setValue(Integer value, boolean fireEvents) {
            this.value = value;
        }

        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
            return null;
        }

        public void fireEvent(GwtEvent<?> event) {  }
    }
}

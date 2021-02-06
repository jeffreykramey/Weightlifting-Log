package com.company;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class LinearRampWidget extends WidgetBase{

    protected LinearRamp lnrRmp;

    public LinearRampWidget(){
        super("LinearRamp");
        lnrRmp = new LinearRamp(50,1000);

        Slider sliderStart = new Slider(0, 10000, 50);
        IntegerProperty startValue = new SimpleIntegerProperty(50);
        sliderStart.valueProperty().bindBidirectional(startValue);

        TextField fieldStart = new TextField();
        fieldStart.textProperty().bind(startValue.asString());

        addToGUI(fieldStart);
        addToGUI(sliderStart);


        Slider sliderStop = new Slider(0, 10000, 1000);
        IntegerProperty stopValue = new SimpleIntegerProperty(1000);
        sliderStop.valueProperty().bindBidirectional(stopValue);

        TextField fieldEnd = new TextField();
        fieldEnd.textProperty().bind(stopValue.asString());

        addToGUI(fieldEnd);
        addToGUI(sliderStop);


        sliderStart.setOnMouseReleased(event -> {
            lnrRmp.setStart((float) sliderStart.getValue());
            setAudioComponent(lnrRmp);
        });

        sliderStop.setOnMouseReleased(event -> {
            lnrRmp.setStop((float) sliderStop.getValue());
            setAudioComponent(lnrRmp);
        });

        setAudioComponent(lnrRmp);
    }

    public LinearRamp getLnrRmp(){
        return lnrRmp;
    }
}

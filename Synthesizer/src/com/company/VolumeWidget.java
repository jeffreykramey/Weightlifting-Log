package com.company;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class VolumeWidget extends WidgetBase{
    private Volume vol;

    public VolumeWidget() {
        super("Volume Control");
        vol = new Volume(1);
        buildInputJack();

        Slider slider = new Slider(0, 2, 1);
        slider.setBlockIncrement(0.1);
        slider.setMajorTickUnit(0.25);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        DoubleProperty sliderValue = new SimpleDoubleProperty(1.0);
        slider.valueProperty().bindBidirectional(sliderValue);

        TextField field = new TextField();
        field.textProperty().bind(sliderValue.asString());

        addToGUI(field);
        addToGUI(slider);

        slider.setOnMouseReleased(event -> {
            vol.setScale(slider.getValue()); //set scale based on current value of slider
            setAudioComponent(vol); //update the AudioComponent member variable.
        });

        setAudioComponent(vol);
    }

    public BorderPane getWidget(){
        return pane;
    }

    public Volume getVol() {
        return vol;
    }
}

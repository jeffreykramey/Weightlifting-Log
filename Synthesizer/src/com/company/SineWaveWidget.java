package com.company;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SineWaveWidget extends WidgetBase{
    protected SineWave sw;

    public SineWaveWidget() throws LineUnavailableException{
        super("SineWave");
        sw = new SineWave(440);

        Slider slider = new Slider(0, 10000, 440);
        IntegerProperty sliderValue = new SimpleIntegerProperty(440);
        slider.valueProperty().bindBidirectional(sliderValue);

        TextField field = new TextField();
        field.textProperty().bind(sliderValue.asString());

        addToGUI(field);
        addToGUI(slider);

        slider.setOnMouseReleased(event -> {
            sw.setPitch((int) slider.getValue());
            setAudioComponent(sw);
        });
        setAudioComponent(sw);
    }

    public AudioComponent getSW(){
        return sw;
    }
}

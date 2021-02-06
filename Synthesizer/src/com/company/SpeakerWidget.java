package com.company;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpeakerWidget extends WidgetBase{
    protected Speaker speaker;
    protected BorderPane pane;

    public SpeakerWidget(){
        super("Speaker");
        speaker = new Speaker();
        buildInputJack();

        setAudioComponent(speaker);


    }
    public BorderPane getSpeaker(){
        return pane;
    }
}

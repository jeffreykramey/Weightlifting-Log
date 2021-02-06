package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Speaker implements AudioComponent{
    protected AudioComponent input;


    @Override
    public AudioClip getClip() {
        return input.getClip();
    }

    @Override
    public boolean hasInput() {
        return input == null;
    }

    @Override
    public void connectInput(AudioComponent input_) {
            input = input_;
    }
}

package com.company;

import javax.sound.sampled.Line;

public class LinearRamp implements AudioComponent {
    private float start;
    private float stop;

    public LinearRamp(float start_, float stop_) {
        start = start_;
        stop = stop_;
    }

    @Override
    public AudioClip getClip() {
        AudioClip adjustedClip = new AudioClip();
        for (int i = 0; i < 88200; i++) {
            adjustedClip.setSample(i, (int) ((start * (adjustedClip.getTotalSamples() - i) + stop * i) / adjustedClip.getTotalSamples()));
        }
        return adjustedClip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input_){
    }

    public void setStart(float newStart){
        start = newStart;
    }

    public void setStop(float newStop){
        stop = newStop;
    }
}

package com.company;

public class Volume implements AudioComponent{

    private double scale;
    private AudioComponent input;

    public void setScale(double newValue){
        scale = newValue;
    }

    public Volume (double scale_) {
        scale = scale_;
    }

    public static int clamp(int adjustedVol){
        return Math.max(Short.MIN_VALUE, Math.min(adjustedVol, Short.MAX_VALUE));
    }

    @Override
    public AudioClip getClip() {
        AudioClip adjustedClip = new AudioClip();
        AudioClip originalClip = input.getClip();
        for (int i = 0; i < 88200 / 2; i++) {
            double adjustedVol = originalClip.getSample(i) * scale;
            adjustedClip.setSample(i, clamp((int)(adjustedVol)));
        }
        return adjustedClip;
    }

    @Override
    public boolean hasInput() {
        return input != null;
    }

    @Override
    public void connectInput(AudioComponent input_) {
          input = input_;
    }

    public double getScale(){
        return scale;
    }

}

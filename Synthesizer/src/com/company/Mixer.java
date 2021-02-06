package com.company;

import java.util.ArrayList;

public class Mixer implements AudioComponent {
    private ArrayList <AudioClip> clipArray;

    public Mixer (){
        clipArray = new ArrayList <AudioClip>();
    }

    public static int clamp(int adjustedVol){
        return Math.max(Short.MIN_VALUE, Math.min(adjustedVol, Short.MAX_VALUE));
    }

    @Override
    public AudioClip getClip() {
        AudioClip adjustedClip = new AudioClip();
        for(int i = 0; i < clipArray.size(); i++){
            for(int j = 0; j < 88200; j++){
                adjustedClip.setSample(j, clamp(adjustedClip.getSample(j) + clipArray.get(i).getSample(j)));
            }
        }
        return adjustedClip;
    }

    @Override
    public boolean hasInput() {
        return clipArray.get(0) != null;
    }

    @Override
    public void connectInput(AudioComponent input) {
        clipArray.add(input.getClip());
    }
}

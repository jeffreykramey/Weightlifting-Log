package com.company;

public class SineWave implements AudioComponent {
    private int pitch;

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int newPitch){
        pitch = newPitch;
    }

    public SineWave(int frequency) {
        pitch = frequency;
    }

    public AudioClip getClip() {
        AudioClip tempClip = new AudioClip();
        for (int i = 0; i < 88200; i++) {
            tempClip.setSample(i, (int) ((Short.MAX_VALUE) * Math.sin(2 * Math.PI * pitch * i / tempClip.getSampleRate())));
        }
        return tempClip;
    }


    @Override
    public boolean hasInput() {
      return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}


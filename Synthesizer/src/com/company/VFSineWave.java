package com.company;

public class VFSineWave implements AudioComponent {
    private AudioComponent input;

    @Override
    public AudioClip getClip() {
        AudioClip adjustedClip = new AudioClip();
        AudioClip originalClip = input.getClip();
        float phase = 0;
        for(int i = 0; i < 88200; i ++){
            phase += 2 * Math.PI * originalClip.getSample(i) / originalClip.getSampleRate();
            adjustedClip.setSample(i, (int) (Short.MAX_VALUE * Math.sin(phase)));
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
}

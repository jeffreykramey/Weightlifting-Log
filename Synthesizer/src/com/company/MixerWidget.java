package com.company;

public class MixerWidget extends WidgetBase{
    protected Mixer mxr;

    public MixerWidget() {
        super("Mixer");
        mxr = new Mixer();
        buildInputJack();
        setAudioComponent(mxr);
    }

    public Mixer getMxr() {
        return mxr;
    }
}

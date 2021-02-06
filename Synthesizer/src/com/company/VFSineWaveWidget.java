package com.company;

public class VFSineWaveWidget extends WidgetBase{
    protected VFSineWave vfsw;

    public VFSineWaveWidget(){
        super("VF SineWave");
        vfsw = new VFSineWave();
        buildInputJack();
        setAudioComponent(vfsw);
    }

    public VFSineWave getVFSW() {
        return vfsw;
    }
}

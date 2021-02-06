

package com.company;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {



    public static void main(String[] args) throws LineUnavailableException {


        Clip c = AudioSystem.getClip();
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

        AudioComponent gen1 = new SineWave(440);
//        AudioComponent gen2 = new SineWave(262);
//        AudioComponent gen3 = new SineWave(392);
//        AudioComponent vol1 = new Volume(.5);
//        AudioComponent vol2 = new Volume(.5);
//        AudioComponent vol3 = new Volume(.5);
//        vol1.connectInput(gen1);
//        vol2.connectInput(gen2);
//        vol3.connectInput(gen3);
//
//        AudioComponent mixer1 = new Mixer();
//        mixer1.connectInput(vol1);
//        mixer1.connectInput(vol2);
//        mixer1.connectInput(vol3);

        AudioComponent ramp1 = new LinearRamp(50,10000);
        AudioComponent VF1 = new VFSineWave();
        VF1.connectInput(ramp1);

        AudioClip clip = VF1.getClip();


        c.open(format16, clip.getByteArr(), 0, clip.getByteArr().length); //reads data from my byte array to play it

        System.out.println("about to play");
        c.start(); //plays it
        //c.loop(1); //plays it 2 more times if desired, so 3 seconds total

        //makes sure the program doesn't quit before the sound plays
        while(c.getFramePosition() < 88200 || c.isActive() || c.isRunning()){}

        System.out.println("done");
        c.close();
    }
}

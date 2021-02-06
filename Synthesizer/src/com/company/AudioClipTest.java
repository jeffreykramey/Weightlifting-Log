package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioClipTest {

    AudioClip test = new AudioClip();




    @Test
    void getDuration() {
        assertEquals(2.0, test.getDuration(), "getDuration failed");
    }

    @Test
    void getSampleRate() {
        assertEquals(44100, test.getSampleRate(),"getSampleRate failed");
    }

    @Test
    void getByteArr() {
    }

    @Test
    void getSample() {
        test.setSample(0, 10);
        assertEquals(10, test.getSample(0), "get sample failed");
    }

    @Test
    void setSample() {
        test.setSample(0, 10);
        assertEquals(10, test.getSample(0), "setSample low failed");

        test.setSample(1, -10);
        assertEquals(-10, test.getSample(1), "setSample low failed");

        test.setSample(2, 257);
        assertEquals(257, test.getSample(2), "setSample failed");

        test.setSample(3, -32768);
        assertEquals(-32768, test.getSample(3), "setSample failed");

        test.setSample(4, 0);
        assertEquals(0, test.getSample(4), "setSample failed");

        test.setSample(5, -2555);
        assertEquals(-2555, test.getSample(5), "setSample failed");

    }
}
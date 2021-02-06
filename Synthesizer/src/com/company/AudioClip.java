package com.company;

public class AudioClip {

    private static double duration = 2.0;
    private static int sampleRate = 44100;
    private static int totalSamples = 88200;
    private byte[] byteArr;

    public AudioClip (){
        byteArr = new byte[176400];
    }

    public double getDuration(){
        return duration;
    }

    public int getSampleRate(){
        return sampleRate;
    }

    public byte[] getByteArr(){
        return byteArr;
    }

    public int getTotalSamples(){
        return totalSamples;
    }


    int getSample(int index){
        short sLow = byteArr[2 * index];
        sLow = (short) (sLow & 0xff);
        short sHigh = byteArr[(2 * index) + 1];
        sHigh = (short) (sHigh << 8);
        int sample = sLow | sHigh;
        return sample;

    }

    void setSample(int index, int value){
        //value = 257
        //int mask = 0x00FF;
        //0000 0000 1111 1111
        byteArr[2 * index] = (byte) value; //set low byte
        //0000 0001 0000 0001 - value
        //0000 0000 1111 1111 - mask
        //0000 0000 0000 0001 - result after &
        //mask = mask << 8;
        //mask now equals 1111 1111 0000 0000
        //short sHigh = (short) (value & mask);
        //0000 0001 0000 0001 - value
        //1111 1111 0000 0000 - mask
        //0000 0001 0000 0000 result after &
        byteArr[(2 * index) + 1] = (byte) (value >> 8); //set high byte
        //sHigh now equals 0000 0000 0000 0001
    }




}

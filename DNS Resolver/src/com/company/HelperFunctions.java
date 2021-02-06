package com.company;

public class HelperFunctions {
    /** Combines a byte[] of two bytes into a single int
     *
     * @param byteArray The byte[] containing the two bytes
     * @return Returns the converted int
     */
    static int combineTwoBytes(byte[] byteArray){
        return byteArray[1] & 0xFF | (byteArray[0]) << 8;
    }

    /** Combines a byte[] of four bytes into a single int
     *
     * @param byteArray The byte[] containing the four bytes
     * @return Returns the converted int
     */
    static int combineFourBytes(byte[] byteArray){
        int byteZero = byteArray[0] << 24;
        byteZero = byteZero & 0xFF000000;
        int byteOne = byteArray[1] << 16;
        byteOne = byteOne & 0x00FF0000;
        int byteTwo = byteArray[2] << 8;
        byteTwo = byteTwo & 0x0000FF00;
        int byteThree = byteArray[3];
        byteThree = byteThree & 0x000000FF;
        int answer = byteZero | byteOne | byteTwo | byteThree;
        return answer;
    }

    /** Splits an int into a byte[] of two bytes
     *
     * @param number The int to be made into a byte[]
     * @return Returns the converted byte[]
     */
    static byte[] splitIntoTwoBytes(int number){
        byte[] byteArray = new byte[2];
        byteArray[1] = (byte) (0x00ff & number);
        byte byteZero = (byte) (number | 0xff00);
        byteArray[0] = (byte) (byteZero >> 8);
        return byteArray;
    }

    /** Helper function to print byte[] into hex for easy comparison
     *
     * @param string The label to print
     * @param byteArray The byte[] to be transformed
     */
    public static void printByteArrayHex(String string, byte[] byteArray) {
        System.out.print(string + " ");
        for (byte b : byteArray) {
            String hex = String.format("%02X", b);
            System.out.print(" " +hex);
        }
        System.out.println("\n");
    }
}

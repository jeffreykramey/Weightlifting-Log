package com.company;

import java.io.*;
import java.util.Arrays;

public class DNSHeader {

    private byte[] id, flags, qdCount, anCount, nsCount, arCount;
    private byte qr, opCode, authAnswer, trunCation, recurDesired, recurAvail, z, authenticData, checkingDisabled, responseCode;

    /** Parses out the parts of a DNSHeader
     *
     * @param inputStream InputStream to read from
     * @return Returns the newly parsed DNSHeader
     */
    static DNSHeader decodeHeader(ByteArrayInputStream inputStream){
        DNSHeader header = new DNSHeader();

        try{
            header.id = inputStream.readNBytes(2);
            header.flags = inputStream.readNBytes(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        header.qr = (byte) (header.flags[0] & 0b10000000);
        header.opCode = (byte) (header.flags[0] & 0b01111000);
        header.authAnswer = (byte) (header.flags[0] & 0b00000100);
        header.trunCation = (byte) (header.flags[0] & 0b00000010);
        header.recurDesired = (byte) (header.flags[0] & 0b00000001);

        header.recurAvail = (byte) (header.flags[1] & 0b10000000);
        header.z = (byte) (header.flags[1] & 0b01000000);
        header.authenticData = (byte) (header.flags[1] & 0b00100000);
        header.checkingDisabled = (byte) (header.flags[1] & 0b00010000);
        header.responseCode = (byte) (header.flags[1] & 0b00001111);

        try{
            header.qdCount = inputStream.readNBytes(2);
            header.anCount = inputStream.readNBytes(2);
            header.nsCount = inputStream.readNBytes(2);
            header.arCount = inputStream.readNBytes(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return header;
    }

    /** Builds a new DNSHeader to be include with a response to the client
     *
     * @param request The client's request which will be used to access its own DNSHeader
     * @param response The response DNSMessage that the header is being built for
     * @return Returns the new DNSHeader to include with the response
     */
    static DNSHeader buildResponseHeader(DNSMessage request, DNSMessage response){
        DNSHeader responseHeader = request.getHeader();

        responseHeader.flags = request.getHeader().flags;
        responseHeader.qr = (byte) 0b10000000;
        responseHeader.recurAvail = (byte) 0b10000000;
        responseHeader.flags[0] = (byte) (responseHeader.flags[0] | 0b10000000);
        responseHeader.flags[1] = (byte) (responseHeader.flags[1] | 0b10000000);

        responseHeader.anCount = HelperFunctions.splitIntoTwoBytes(response.getAnswers().size());
        responseHeader.nsCount = HelperFunctions.splitIntoTwoBytes(response.getAuthorityRecords().size());
        responseHeader.arCount = HelperFunctions.splitIntoTwoBytes(response.getAdditionalRecords().size());

        return responseHeader;
    }

    /** Writes the header bytes to be sent to the client
     *
     * @param outputStream The InputStream to read from
     */
    void writeBytes(ByteArrayOutputStream outputStream){
        try {
            outputStream.write(id);
            outputStream.write(flags);
            outputStream.write(qdCount);
            outputStream.write(anCount);
            outputStream.write(nsCount);
            outputStream.write(arCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Transforms a DNSHeader into an easily readable format
     *
     * @return String format of a DNSHeader
     */
    @Override
    public String toString() {
        return "DNSHeader{" +
                "id=" + Arrays.toString(id) +
                ", flags=" + Arrays.toString(flags) +
                ", qdCount=" + Arrays.toString(qdCount) +
                ", anCount=" + Arrays.toString(anCount) +
                ", nsCount=" + Arrays.toString(nsCount) +
                ", arCount=" + Arrays.toString(arCount) +
                ", qr=" + qr +
                ", opCode=" + opCode +
                ", authAnswer=" + authAnswer +
                ", trunCation=" + trunCation +
                ", recurDesired=" + recurDesired +
                ", recurAvail=" + recurAvail +
                ", z=" + z +
                ", authenticData=" + authenticData +
                ", checkingDisabled=" + checkingDisabled +
                ", responseCode=" + responseCode +
                '}';
    }

    /**  Returns qdCount member var
     *
     * @return this.qdCount
     */
    public int getQuestionCount() {
        return HelperFunctions.combineTwoBytes(qdCount);
    }

    /** Returns anCount member var
     *
     * @return this.anCount
     */
    public int getAnswerCount(){
        return HelperFunctions.combineTwoBytes(anCount);
    }

    /** Returns nsCount member var
     *
     * @return this.nsCount
     */
    public int getAuthorityRecordCount(){
        return HelperFunctions.combineTwoBytes(nsCount);
    }

    /** Returns arCount member var
     *
     * @return this.arCount
     */
    public int getAdditionalRecordCount(){
        return HelperFunctions.combineTwoBytes(arCount);
    }

    /** Returns responseCode member var
     *
     * @return this.responseCode
     */
    public byte getResponseCode() {
        return responseCode;
    }
}

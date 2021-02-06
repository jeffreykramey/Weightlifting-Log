package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class DNSRecord {
    private long date = System.currentTimeMillis();
    private String[] name;
    private byte[] type, class_, ttl, rdLength, rData;

    /** Parses out the parts of a DNSRecord
     *
     * @param inputStream InputStream to read from
     * @param message The DNSMessage being built
     * @return Returns the newly parsed DNSRecord
     */
    static DNSRecord decodeRecord(InputStream inputStream, DNSMessage message) throws IOException {
        DNSRecord record = new DNSRecord();
        record.name = message.readDomainName(inputStream);
        try{
            record.type = inputStream.readNBytes(2);
            record.class_ = inputStream.readNBytes(2);
            record.ttl = inputStream.readNBytes(4);
            record.rdLength = inputStream.readNBytes(2);
            int rDataLen = HelperFunctions.combineTwoBytes(record.rdLength);
            record.rData = inputStream.readNBytes(rDataLen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return record;
    }

    /** Writes the DNSRecord bytes to be sent to the client
     *
     * @param outputStream OutputStream to write to
     * @param domainLocations A hashmap that contains the offset for compressed domain names
     */
    void writeBytes(ByteArrayOutputStream outputStream, HashMap <String, Integer> domainLocations){
        DNSMessage.writeDomainName(outputStream, domainLocations, name);
        try{
            outputStream.write(type);
            outputStream.write(class_);
            outputStream.write(ttl);
            outputStream.write(rdLength);
            outputStream.write(rData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Transforms a DNSRecord into an easily readable format
     *
     * @return
     */
    @Override
    public String toString() {
        return "DNSRecord{" +
                "date=" + date +
                ", name=" + Arrays.toString(name) +
                ", type=" + Arrays.toString(type) +
                ", class_=" + Arrays.toString(class_) +
                ", ttl=" + Arrays.toString(ttl) +
                ", rdLength=" + Arrays.toString(rdLength) +
                ", rData=" + Arrays.toString(rData) +
                '}';
    }

    /** Checks if the DNSRecord creation date is past the packet's time to live
     *
     * @return Returns if the timestamp is valid or not
     */
    boolean timestampValid(){

        int parsedTTL = HelperFunctions.combineFourBytes(ttl);
        parsedTTL *= 1000;
        return (date + parsedTTL) > System.currentTimeMillis();
    }
}

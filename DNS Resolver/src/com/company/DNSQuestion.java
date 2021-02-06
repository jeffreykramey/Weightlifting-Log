package com.company;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class DNSQuestion {
    private String[] qName;
    private byte[] qType, qClass;

    /** Parses out the parts of a DNSQuestion
     *
     * @param inputStream InputStream to read from
     * @param message The DNSMessage being built
     * @return Returns the newly parsed DNSQuestion
     */
    static DNSQuestion decodeQuestion(ByteArrayInputStream inputStream, DNSMessage message) throws IOException {
        DNSQuestion question = new DNSQuestion();
        question.qName = message.readDomainName(inputStream);
        try {
            question.qType = inputStream.readNBytes(2);
            question.qClass = inputStream.readNBytes(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return question;
    }

    /** Writes the question bytes to be sent to the client
     *
     * @param outputStream OutputStream to write to
     * @param domainNameLocations A hashmap that contains the offset for compressed domain names
     */
    void writeBytes(ByteArrayOutputStream outputStream, HashMap<String,Integer> domainNameLocations) {
        DNSMessage.writeDomainName(outputStream, domainNameLocations, qName);
        outputStream.writeBytes(qType);
        outputStream.writeBytes(qClass);
    }

    /** Transforms a DNSQuestion into an easily readable format
     *
     * @return
     */
    @Override
    public String toString() {
        return "DNSQuestion{" +
                "qName=" + Arrays.toString(qName) +
                ", qType=" + Arrays.toString(qType) +
                ", qClass=" + Arrays.toString(qClass) +
                '}';
    }

    /** Compares two objects and checks for equality
     *
     * @param o An object to compare this to
     * @return Whether or not the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNSQuestion question = (DNSQuestion) o;
        return Arrays.equals(qName, question.qName) && Arrays.equals(qType, question.qType) && Arrays.equals(qClass, question.qClass);
    }

    /** Hashing function for use with DNSCache
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(qName);
        result = 31 * result + Arrays.hashCode(qType);
        result = 31 * result + Arrays.hashCode(qClass);
        return result;
    }

}

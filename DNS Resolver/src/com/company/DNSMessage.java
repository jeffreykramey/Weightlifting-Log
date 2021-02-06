package com.company;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DNSMessage {
    private DNSHeader header;
    private DNSQuestion[] questions;
    private ArrayList<DNSRecord> answers = new ArrayList<>();
    private ArrayList<DNSRecord> authorityRecords = new ArrayList<>();
    private ArrayList<DNSRecord> additionalRecords = new ArrayList<>();
    private byte[] entireMessage;

    /** Parses a byte[] into various sections that make up a DNSMessage
     *
     * @param messageAsByteArray The byte[] that will be made into a DNSMessage
     * @return Returns the new DNSMesasge
     * @throws IOException
     */
    static DNSMessage decodeMessage(byte[] messageAsByteArray) throws IOException {
        DNSMessage message = new DNSMessage();
        message.entireMessage = messageAsByteArray;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(messageAsByteArray);
        message.header = DNSHeader.decodeHeader(inputStream);

        int questionCount = message.header.getQuestionCount();
        DNSQuestion[] questionArray = new DNSQuestion[questionCount];
        for(int i = 0; i < questionCount; i++){
            questionArray[i] = DNSQuestion.decodeQuestion(inputStream, message);
        }
        message.questions = questionArray;

        int answerCount = message.header.getAnswerCount();
        for(int i = 0; i < answerCount; i++){
            message.getAnswers().add(DNSRecord.decodeRecord(inputStream, message));
        }

        int authorityRecordCount = message.header.getAuthorityRecordCount();
        for(int i = 0; i < authorityRecordCount; i++){
            message.getAuthorityRecords().add(DNSRecord.decodeRecord(inputStream, message));
        }

        int additionalRecordCount = message.header.getAdditionalRecordCount();
        for(int i = 0; i < additionalRecordCount; i++){
            message.getAdditionalRecords().add(DNSRecord.decodeRecord(inputStream, message));
        }
        return message;
    }

    /** Reads in from the InputStream and parses the parts of the domain name and stores them into a String[]
     *
     * @param inputStream The InputStream to read from
     * @return Return the String[] that holds each piece of the domain name
     */
    String[] readDomainName(InputStream inputStream)throws IOException{

        ArrayList<String> qNameStringArrayList = new ArrayList<>();
        byte octet = (byte) inputStream.read();
        if(octet == (byte) 0xc0){ //check for compression
            return readDomainName(inputStream.read());
        }
        else {
            while (octet != 0) {
                StringBuilder currentString = new StringBuilder();
                for (int i = 0; i < octet; i++) {
                    currentString.append((char) inputStream.read());
                }
                qNameStringArrayList.add(currentString.toString());
                try {
                    octet = (byte) inputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] qNameStringArray = new String[qNameStringArrayList.size()];
        for(int j = 0; j < qNameStringArray.length; j++){
            qNameStringArray[j] = qNameStringArrayList.get(j);
        }
        return qNameStringArray;
    }

    /** Reads from a specific part of an input stream to handle situations where a domain name is compressed
     *
     * @param firstByte The offset where the compressed name begins
     * @return Calls the other version of readDomainName to continue the conversion to a String[]
     * @throws IOException
     */
    String[] readDomainName(int firstByte) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(entireMessage);
        inputStream.readNBytes(firstByte);
        return readDomainName(inputStream);
    }

    /** Builds each part of a DNSMessage to be sent back to a client
     *
     * @param request The original query from the client
     * @param answers The ArrayList of answers that are retrieved from the DNSCache or Google's DNS server
     * @return Returns the newly crafted DNSMessge response
     */
    static DNSMessage buildResponse(DNSMessage request, ArrayList<DNSRecord> answers){
        DNSMessage response = new DNSMessage();
        response.questions = request.getQuestion();
        response.answers = answers;
        response.additionalRecords = request.getAdditionalRecords();
        response.authorityRecords = request.getAuthorityRecords();
        response.header = DNSHeader.buildResponseHeader(request, response);

        return response;
    }

    /** Converts the message to a byte[] to be sent over a socket
     *
     * @return Returns the DNSMessage as a byte[]
     * @throws IOException
     */
    byte[] toBytes() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HashMap<String, Integer> domainNameLocations = new HashMap<>();
        header.writeBytes(outputStream);
        for(DNSQuestion q: questions){
            q.writeBytes(outputStream, domainNameLocations);
        }

        for(DNSRecord answer : answers){
            answer.writeBytes(outputStream, domainNameLocations);

        }
        for(DNSRecord authorityRecords : authorityRecords){
            authorityRecords.writeBytes(outputStream, domainNameLocations);
        }
        for(DNSRecord additionalRecords : additionalRecords){
            additionalRecords.writeBytes(outputStream, domainNameLocations);
        }
        return outputStream.toByteArray();
    }

    /** If the domain name is seen for the first time, add the string and outputstream size to a hashmap and write
     * the length of each domain piece followed by its characters. If the domain name exists in the hashmap, right the
     * compression octet and offset where it can be found
     *
     * @param outputStream ByteArrayOutputStream to write back to the client
     * @param domainLocations HashMap contains URLs and offset location
     * @param domainPieces String[] that make up a URL: www.google.com -> www, google, com
     * @throws IOException
     */
    static void writeDomainName(ByteArrayOutputStream outputStream, HashMap<String,Integer> domainLocations, String[] domainPieces){
        String url = octetsToString(domainPieces);
            if(domainLocations.containsKey(url)){
                byte[] offset = HelperFunctions.splitIntoTwoBytes(domainLocations.get(url));
                offset[0] = (byte) (offset[0] | 0xc0);
                try {
                    outputStream.write(offset);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //split parts off each domain piece??
            else {
                domainLocations.put(url, outputStream.size());
                for (String s : domainPieces){
                    outputStream.write((byte) s.length());
                    for(char c : s.toCharArray()){
                        outputStream.write((byte) c);
                    }
                }
                outputStream.write(0);
            }
    }

    /** Turns a String[] int a URL: www, google, com -> www.google.com
     *
     * @param domainPieces individual parts of a URL
     * @return Returns the converted String
     */
//Join the pieces of the domain name into proper URL format i.e. (["utah", "edu"] -> "utah.edu" )
    static String octetsToString(String[] domainPieces){
        StringBuilder url = new StringBuilder();
        for(int i = 0; i < domainPieces.length; i++){
            url.append(domainPieces[i]);
            if(i < domainPieces.length - 1){
                url.append('.');
            }
        }
        return url.toString();
    }

    /** Helper method that prints each part of a given DNSMessage
     *
     * @throws IOException
     */
    void messagePrinter() throws IOException {
        System.out.println("header: " + getHeader().toString());
        System.out.println("questions: " + Arrays.toString(getQuestion()));
        System.out.println("answers: " +getAnswers().toString());
        System.out.println("additional records: " + getAdditionalRecords());
        System.out.println("authority records: " + getAuthorityRecords());
        HelperFunctions.printByteArrayHex("byte[]: ", toBytes());
    }

    /** Returns header member var
     *
     * @return this.header
     */
    DNSHeader getHeader(){
        return header;
    }

    /** Returns questions member var
     *
     * @return this.questions
     */
    DNSQuestion[] getQuestion(){
        return questions;
    }

    /** Returns answers member var
     *
     * @return this.answers
     */
    ArrayList<DNSRecord> getAnswers(){
        return answers;
    }

    /** Returns additionalRecords member var
     *
     * @return this.addtionalRecords
     */
    public ArrayList<DNSRecord> getAdditionalRecords() {
        return additionalRecords;
    }

    /** Returns authorityRecords member var
     *
     * @return this.authorityRecords
     */
    public ArrayList<DNSRecord> getAuthorityRecords() {
        return authorityRecords;
    }
}




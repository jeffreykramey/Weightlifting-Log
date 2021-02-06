package com.company;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class DNSServer {
    protected DatagramSocket datagramSocket;

    void readPacket() throws IOException {
        System.out.println("server listening....");
        datagramSocket = new DatagramSocket(8053);
        byte[] receivedData = new byte[512];
        DNSCache recordCache = new DNSCache();

        while(true){
            boolean goodDomainName = true;
            DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            datagramSocket.receive(receivedPacket);
            InetAddress clientIP = receivedPacket.getAddress();
            int clientPort = receivedPacket.getPort();
            System.out.println("data received");

            DNSMessage queryMessage = DNSMessage.decodeMessage(receivedData);

            ArrayList<DNSRecord> answerArray = new ArrayList<>();
            for(DNSQuestion question : queryMessage.getQuestion()){
                DNSRecord answerToQuestion = recordCache.queryCache(question);
                if(answerToQuestion == null){
                    System.out.println("Couldn't find answer, asking Google...");
                    DNSMessage googleResponse = sendQueryToGoogle(receivedData);
                    if(googleResponse.getHeader().getResponseCode() == 3){ //requested domain name doesn't exist, forward google's response
                        System.out.println("domain name doesn't exist\n\n");
                        byte[] responseByteArray = googleResponse.toBytes();
                        DatagramPacket responsePacket = new DatagramPacket(responseByteArray, responseByteArray.length, clientIP, clientPort);
                        datagramSocket.send(responsePacket);
                        goodDomainName = false;
                        continue;
                    }
                    answerToQuestion = googleResponse.getAnswers().get(0);
                    recordCache.addRecordToCache(question, answerToQuestion);
                }
                answerArray.add(answerToQuestion);
            }

            if(goodDomainName){//requested domain name exists, build response
                DNSMessage responseMessage = DNSMessage.buildResponse(queryMessage, answerArray);
                byte[] responseByteArray = responseMessage.toBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseByteArray, responseByteArray.length, clientIP, clientPort);
                datagramSocket.send(responsePacket);
                System.out.println("packet sent\n\n");
            }
            receivedData = new byte[512]; //reset the byte array, maybe not needed
        }
    }

    /** Forwards a query to Google DNS servers if answer is not found in local cache
     *
     * @param message Original query to forwarded to Google DNS servers
     * @return returns Google DNS server response
     * @throws IOException
     */
    DNSMessage sendQueryToGoogle(byte[] message) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        byte[] googleResponse = new byte[512];
        InetAddress googleIP = InetAddress.getByName("8.8.8.8");
        DatagramPacket queryPacket = new DatagramPacket(message, message.length, googleIP, 53);
        datagramSocket.send(queryPacket);
        DatagramPacket responsePacket = new DatagramPacket(googleResponse, googleResponse.length);
        datagramSocket.receive(responsePacket);
        return DNSMessage.decodeMessage(googleResponse);
    }






}






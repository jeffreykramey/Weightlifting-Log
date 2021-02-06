package com.company;

import java.io.IOException;
import java.util.HashMap;

public class DNSCache {
    private HashMap<DNSQuestion, DNSRecord> cache = new HashMap<DNSQuestion, DNSRecord>();

    /** Adds a question/record combo to the cache as an alternative to querying cloudflare or google DNS servers
     *
     * @param question The DNSQuestion to be added
     * @param answer The DNSRecord to be added
     */
    void addRecordToCache(DNSQuestion question, DNSRecord answer){
        cache.put(question, answer);
        System.out.println("Answer added to cache!");
    }

    /** Checks to see if a DNSQuestion exists in the cache and verifies a valid TTL
     *
     * @param question DNSQuestion used as a key for searching cache
     * @return Returns a DNSRecord if a valid DNSQuestion is found, otherwise returns null
     * @throws IOException
     */
    DNSRecord queryCache(DNSQuestion question) throws IOException {
        if (cache.containsKey(question)) {
            System.out.println("Answer found in cache!");
            if (cache.get(question).timestampValid()) {
                System.out.println("TTL valid!");
                return cache.get(question);
            }
            else {
                cache.remove(question);
                System.out.println("TTL not valid!");
            }
        }
        return null;
    }

}

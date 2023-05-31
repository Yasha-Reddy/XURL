package com.crio.shorturl;

import java.util.HashMap;

public class XUrlImpl implements XUrl{

    private HashMap<String,String> mapping = new HashMap<>();
    private HashMap<String,String> inverseMapping = new HashMap<>();

    private String randomString(){
        String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<9;i++){
            int ch = (int)(alphaNumeric.length()*Math.random());
            sb.append(alphaNumeric.charAt(ch));
        }
        return sb.toString();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        String shortUrl = "http://short.url/"+randomString();
        mapping.putIfAbsent(longUrl, shortUrl);
        inverseMapping.putIfAbsent(shortUrl, longUrl);
        return mapping.get(longUrl);
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        for(String s : mapping.keySet()){
            if(mapping.get(s).equals(shortUrl))
                return null;
        }
        mapping.putIfAbsent(longUrl, shortUrl);
        inverseMapping.putIfAbsent(shortUrl, longUrl);
        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl = null;
        for(String s : mapping.keySet()){
            if(mapping.get(s).equals(shortUrl)){
                longUrl = s;
            }
        }
        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        Integer count =0;
        for(String s : inverseMapping.keySet()){
            if(inverseMapping.get(s).equals(longUrl))
                count++;
        }
        return count;
    }

    @Override
    public String delete(String longUrl) {
        String shortUrl = mapping.remove(longUrl);
        inverseMapping.remove(shortUrl);
        return longUrl;
    }
    
}
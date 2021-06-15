package com.fastwok.crawler.services.isservice;

import java.io.IOException;

public interface SmsService{
    public String sendSMS(String to, String content, int type, String sender) throws IOException;
}

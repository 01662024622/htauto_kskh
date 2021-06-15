package com.fastwok.crawler.services;

import com.mashape.unirest.http.exceptions.UnirestException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface BaseService {
    public void runQueue();
}

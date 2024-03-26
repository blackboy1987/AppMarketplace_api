package com.bootx.service;

/**
 * @author black
 */
public interface EmailService {
    boolean send(String toEmail,String code);
}

package com.mail.sender.service;

public interface EmailSender<T> {
    void send(T messageDetails, String message);
}

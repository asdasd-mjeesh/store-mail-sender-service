package com.mail.sender.service.senders;

public interface EmailSender<T> {
    void send(T messageDetails, String message);
}

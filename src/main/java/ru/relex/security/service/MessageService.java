package ru.relex.security.service;

import ru.relex.entity.User;

public interface MessageService {
    void addMessage(User senderUser, User recipientUser, String message);
}

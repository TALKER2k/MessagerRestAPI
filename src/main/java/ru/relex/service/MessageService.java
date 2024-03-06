package ru.relex.service;

import ru.relex.models.Message;
import ru.relex.models.User;

import java.util.List;

public interface MessageService {
    void addMessage(User senderUser, User recipientUser, String message);
    void deleteAllList(User userSender, User userRecipient);
    List<Message> getAllList(User userSender, User userRecipient);
}

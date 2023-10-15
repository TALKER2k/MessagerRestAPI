package ru.relex.service.Impl;

import org.springframework.stereotype.Service;
import ru.relex.entity.Conversation;
import ru.relex.entity.Message;
import ru.relex.entity.User;
import ru.relex.repository.ConversationRepository;
import ru.relex.repository.MessageRepository;
import ru.relex.repository.UserRepository;
import ru.relex.service.MessageService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {
    final UserRepository userRepository;
    final ConversationRepository conversationRepository;
    final MessageRepository messageRepository;

    public MessageServiceImpl(UserRepository userRepository,
                              ConversationRepository conversationRepository,
                              MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    @Override
    public void addMessage(User senderUser, User recipientUser, String message) {
        if (message == null || message.isEmpty())
            return;
        boolean flag = false;
        if (senderUser.getId() > recipientUser.getId()) {
            User temp = recipientUser;
            recipientUser = senderUser;
            senderUser = temp;
            flag = true;
        }
        List<Conversation> conversationList;
        conversationList = conversationRepository
                .findByFirstIdAndSecondId(senderUser, recipientUser);

        if (conversationList.isEmpty()) {
            Conversation newConversation = new Conversation();
            newConversation.setFirstId(senderUser);
            newConversation.setSecondId(recipientUser);
            conversationRepository.save(newConversation);
            conversationList.add(newConversation);
        }

        Conversation conversation = conversationList.get(0);

        Message newMessage = new Message();
        newMessage.setConversation(conversation);
        if ((flag)) {
            newMessage.setSender(recipientUser);
        } else {
            newMessage.setSender(senderUser);
        }
        newMessage.setMessageText(message);
        newMessage.setSentAt(LocalDateTime.now());
        messageRepository.save(newMessage);
    }

    @Override
    public List<Message> getAllList(User userSender, User userRecipient) {
        if (userSender.getId() > userRecipient.getId()) {
            User temp = userSender;
            userSender = userRecipient;
            userRecipient = temp;
        }
        return messageRepository.findByConversationId(conversationRepository.findByFirstIdAndSecondId(userSender, userRecipient).get(0).getId());
    }

    @Transactional
    @Override
    public void deleteAllList(User userSender, User userRecipient) {
        if (userSender.getId() > userRecipient.getId()) {
            User temp = userSender;
            userSender = userRecipient;
            userRecipient = temp;
        }
        List<Message> listMessages = messageRepository.findByConversationId(conversationRepository.findByFirstIdAndSecondId(userSender, userRecipient).get(0).getId());
        for (Message  mes : listMessages) {
            messageRepository.delete(mes);
        }
    }
}

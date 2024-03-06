package ru.relex.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.relex.models.User;
import ru.relex.repository.ConversationRepository;
import ru.relex.repository.MessageRepository;
import ru.relex.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserAccountCleanupScheduler {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public UserAccountCleanupScheduler(UserRepository userRepository, ConversationRepository conversationRepository,
                                       MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // Запуск каждую 1 min
    public void cleanupInactiveUserAccounts() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(1);
        List<User> inactiveUsers = userRepository.findInactiveUsers(tenMinutesAgo);
        for (User user : inactiveUsers) {
            conversationRepository.deleteByFirstId(user);
            conversationRepository.deleteBySecondId(user);
            messageRepository.deleteBySender(user);
            userRepository.delete(user);
        }
    }
}

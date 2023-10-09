package ru.relex.security.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.relex.entity.User;
import ru.relex.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserAccountCleanupScheduler {

    private final UserRepository userRepository;

    public UserAccountCleanupScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 300000) // Запуск каждые 5 min
    public void cleanupInactiveUserAccounts() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(1);
        List<User> inactiveUsers = userRepository.findInactiveUsers(tenMinutesAgo);
        for (User user : inactiveUsers) {
            userRepository.delete(user);
        }
    }
}

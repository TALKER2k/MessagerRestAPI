package ru.relex.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.relex.entity.User;
import ru.relex.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserAccountCleanupScheduler {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 60000) // Запуск каждые 1 min
    public void cleanupInactiveUserAccounts() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(1);
        List<User> inactiveUsers = userRepository.findInactiveUsers(tenMinutesAgo);
        for (User user : inactiveUsers) {
            userRepository.delete(user); // Удалить неактивных пользователей
        }
    }
}

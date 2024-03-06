package ru.relex.service;

import ru.relex.models.User;

import java.util.List;
import java.util.Set;

public interface FriendService {
    User addFriend(User user, User friend);
    User findUserByUsername(String username);
    Set<User> showFriend(String username);
}

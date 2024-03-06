package ru.relex.service.Impl;

import org.springframework.stereotype.Service;
import ru.relex.models.User;
import ru.relex.repository.UserRepository;
import ru.relex.service.FriendService;

import java.util.List;
import java.util.Set;

@Service
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;

    public FriendServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addFriend(User user, User friend) {
        if (!user.getId().equals(friend.getId())) {
            Set<User> userFriends = user.getFriends();
            userFriends.add(friend);
            user.setFriends(userFriends);
            userRepository.save(user);

            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Set<User> showFriend(String username) {
        return userRepository.findByUsername(username).get().getFriends();
    }

}

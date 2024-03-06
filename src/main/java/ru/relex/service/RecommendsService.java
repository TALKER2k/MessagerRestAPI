package ru.relex.service;

import ru.relex.models.User;

import java.util.List;

public interface RecommendsService {
    List<User> getRecommendsFriends(User user);
}

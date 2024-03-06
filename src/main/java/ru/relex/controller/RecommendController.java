package ru.relex.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.models.User;
import ru.relex.repository.UserRepository;
import ru.relex.service.RecommendsService;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendsService recommendsService;
    private final UserRepository userRepository;

    public RecommendController(RecommendsService recommendsService, UserRepository userRepository) {
        this.recommendsService = recommendsService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getRecommendationUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(null);
        return recommendsService.getRecommendsFriends(user);
    }
}

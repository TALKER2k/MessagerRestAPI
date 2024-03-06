package ru.relex.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.relex.models.User;
import ru.relex.payload.request.AddFriendRequest;
import ru.relex.service.FriendService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public User addFriend(@Valid @RequestBody AddFriendRequest friend) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = friendService.findUserByUsername(authentication.getName());

        return friendService.addFriend(user, friendService.findUserByUsername(friend.getUsernameFriend()));
    }

    @GetMapping("/myFriends")
    public Set<User> getMyFriends() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return friendService.showFriend(auth.getName());
    }
}

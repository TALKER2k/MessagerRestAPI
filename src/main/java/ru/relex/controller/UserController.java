package ru.relex.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.relex.entity.User;
import ru.relex.payload.request.ChangePasswordRequest;
import ru.relex.payload.request.ChangeProfileRequest;
import ru.relex.payload.response.MessageResponse;
import ru.relex.repository.UserRepository;
import ru.relex.security.jwt.JwtUtils;
import ru.relex.service.Impl.UserDetailsImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profile/settings")
public class UserController {
    final UserRepository userRepository;

    final PasswordEncoder encoder;

    final JwtUtils jwtUtils;

    public UserController(UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody ChangePasswordRequest passwordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(userDetails.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(encoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("You've successfully changed password!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Unknown error!"));
        }
    }

    @PostMapping("/main")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> changeProfile(@Valid @RequestBody ChangeProfileRequest profileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(userDetails.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean flag = false;
            if (!("".equals(profileRequest.getUsername()))) {
                user.setUsername(profileRequest.getUsername());
                flag = true;
            }
            if (!("".equals(profileRequest.getEmail()))) {
                user.setEmail(profileRequest.getEmail());
            }
            if (!("".equals(profileRequest.getFirstname()))) {
                user.setFirstname(profileRequest.getFirstname());
            }
            if (!("".equals(profileRequest.getLastname()))) {
                user.setLastname(profileRequest.getLastname());
            }
            userRepository.save(user);
            if (flag) {
                jwtUtils.getCleanJwtCookie();
            }
            return ResponseEntity.ok(new MessageResponse("You've successfully changed profile!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Unknown error!"));
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> deleteProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(userDetails.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive("No active");
            user.setLastActivityTime(LocalDateTime.now());
            userRepository.save(user);
            ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new MessageResponse("You've successfully delete account!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Unknown error!"));
        }
    }
}

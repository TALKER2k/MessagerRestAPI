package ru.relex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.relex.entity.Message;
import ru.relex.entity.User;
import ru.relex.payload.request.MessageRequest;
import ru.relex.payload.request.MessageShowRequest;
import ru.relex.payload.response.MessageResponse;
import ru.relex.repository.UserRepository;
import ru.relex.service.Impl.MessageServiceImpl;
import ru.relex.service.Impl.UserDetailsImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/message")
public class SocialController {

    final UserRepository userRepository;
    final MessageServiceImpl messageServiceImpl;

    public SocialController(UserRepository userRepository, MessageServiceImpl messageServiceImpl) {
        this.userRepository = userRepository;
        this.messageServiceImpl = messageServiceImpl;
    }

    @PostMapping("/send")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest) {
        if (userRepository.findByUsername(messageRequest.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Not correct username!"));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var userSender = userRepository.findByUsername(userDetails.getUsername());
        var userRecipient = userRepository.findByUsername(messageRequest.getUsername());
        messageServiceImpl.addMessage(userSender.get(), userRecipient.get(), messageRequest.getMessage());
        return ResponseEntity.ok(new MessageResponse("You've successfully send message!"));
    }

    @PostMapping("/show")
    @PreAuthorize("hasRole('USER')")
    public List<MessageRequest> showMessages(@Valid @RequestBody MessageShowRequest messageShowRequest) {
        if (userRepository.findByUsername(messageShowRequest.getUsername()).isEmpty()) {
            return new ArrayList<>();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var userSender = userRepository.findByUsername(userDetails.getUsername());
        var userRecipient = userRepository.findByUsername(messageShowRequest.getUsername());
        List<Message> allMessages = messageServiceImpl.getAllList(userSender.get(), userRecipient.get());
        List<MessageRequest> filteredMessages = allMessages.stream()
                .map(message -> {
                    MessageRequest messageDTO = new MessageRequest();
                    messageDTO.setUsername(message.getSender().getUsername());
                    messageDTO.setMessage(message.getMessageText());
                    return messageDTO;
                })
                .collect(Collectors.toList());

        return filteredMessages;

    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> deleteChat(@Valid @RequestBody MessageShowRequest messageShowRequest) {
        if (userRepository.findByUsername(messageShowRequest.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Not correct username!"));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var userSender = userRepository.findByUsername(userDetails.getUsername());
        var userRecipient = userRepository.findByUsername(messageShowRequest.getUsername());
        messageServiceImpl.deleteAllList(userSender.get(), userRecipient.get());
        return ResponseEntity.ok(new MessageResponse("You've successfully delete chat!"));

    }
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageRequest processMessageFromClient(MessageRequest messageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var userSender = userRepository.findByUsername(userDetails.getUsername());
        var userRecipient = userRepository.findByUsername(messageRequest.getUsername());
        messageServiceImpl.addMessage(userSender.get(), userRecipient.get(), messageRequest.getMessage());
        return messageRequest;
    }
}

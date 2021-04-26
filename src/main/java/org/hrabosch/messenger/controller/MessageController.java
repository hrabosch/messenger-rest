package org.hrabosch.messenger.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.hrabosch.messenger.config.properties.JwtTokenProperties;
import org.hrabosch.messenger.entity.UserMessage;
import org.hrabosch.messenger.model.message.SendMessageRequest;
import org.hrabosch.messenger.model.message.SendMessageResponse;
import org.hrabosch.messenger.security.JwtTokenProvider;
import org.hrabosch.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtTokenProperties jwtTokenProperties;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/send")
    public SendMessageResponse sendMessage(@RequestHeader HttpHeaders headers, @Valid @RequestBody SendMessageRequest sendMessageRequest) {
        String token = headers.getFirst(jwtTokenProperties.getAuthorizationHeader());
        UserMessage messagePayload = messageService.sendMessage(sendMessageRequest, jwtTokenProvider.getUsername(token));

        return new SendMessageResponse(messagePayload,new Date());
    }

    @GetMapping("/sent")
    public List<UserMessage> getAllSentMessages(@RequestHeader HttpHeaders headers) {
        String username = jwtTokenProvider.getUsername(headers.getFirst(jwtTokenProperties.getAuthorizationHeader()));
        return messageService.getMessagesBySender(username);
    }

    @GetMapping("/inbox")
    public List<UserMessage> getAllInboxMessages(@RequestHeader HttpHeaders headers) {
        String username = jwtTokenProvider.getUsername(headers.getFirst(jwtTokenProperties.getAuthorizationHeader()));
        return messageService.getMessagesByRecipient(username);
    }

    @DeleteMapping("/{uuid}")
    public UserMessage removeReceivedMessage(@PathVariable UUID uuid, @RequestHeader HttpHeaders headers) {
        String username = jwtTokenProvider.getUsername(headers.getFirst(jwtTokenProperties.getAuthorizationHeader()));
        return messageService.removeReceivedMessage(uuid, username);
    }

}

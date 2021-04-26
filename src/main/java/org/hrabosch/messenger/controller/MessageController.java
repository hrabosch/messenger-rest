package org.hrabosch.messenger.controller;

import java.util.Date;

import javax.validation.Valid;

import org.hrabosch.messenger.config.properties.JwtTokenProperties;
import org.hrabosch.messenger.entity.UserMessage;
import org.hrabosch.messenger.model.message.SendMessageRequest;
import org.hrabosch.messenger.model.message.SendMessageResponse;
import org.hrabosch.messenger.security.JwtTokenProvider;
import org.hrabosch.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

}

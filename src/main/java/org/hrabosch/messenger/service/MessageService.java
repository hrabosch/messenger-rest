package org.hrabosch.messenger.service;

import java.util.UUID;

import org.hrabosch.messenger.entity.UserMessage;
import org.hrabosch.messenger.exception.InvalidRecipientException;
import org.hrabosch.messenger.model.message.SendMessageRequest;
import org.hrabosch.messenger.repository.MessageRepository;
import org.hrabosch.messenger.repository.MessengerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessengerUserRepository userRepository;

    public UserMessage sendMessage(SendMessageRequest sendMessageRequest, String sender) {
        if (!userRepository.existsById(sendMessageRequest.getRecipient()))
            throw new InvalidRecipientException(sendMessageRequest.getRecipient());
        UserMessage message = new UserMessage();
        message.setUuid(UUID.randomUUID());
        message.setContent(sendMessageRequest.getPayload());
        message.setReceiver(sendMessageRequest.getRecipient());
        // TODO Only chance for NPE is removing user during still active token, which will be disabled and removed anyway -> invalid
        message.setSender(userRepository.findByUsername(sender).getUuid());
        return messageRepository.save(message);
    }
}

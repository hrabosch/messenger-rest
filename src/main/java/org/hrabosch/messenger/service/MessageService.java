package org.hrabosch.messenger.service;

import java.util.List;
import java.util.UUID;

import org.hrabosch.messenger.entity.MessengerUser;
import org.hrabosch.messenger.entity.UserMessage;
import org.hrabosch.messenger.exception.InvalidRecipientException;
import org.hrabosch.messenger.exception.MessageNotFoundException;
import org.hrabosch.messenger.exception.UnauthorizedOperation;
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
        message.setRecipient(sendMessageRequest.getRecipient());
        // TODO Only chance for NPE is removing user during still active token, which will be disabled and removed anyway -> invalid
        message.setSender(userRepository.findByUsername(sender).getUuid());
        return messageRepository.save(message);
    }

    public List<UserMessage> getMessagesByRecipient(String username) {
        MessengerUser user = userRepository.findByUsername(username);

        return messageRepository.findByRecipient(user.getUuid());
    }

    public List<UserMessage> getMessagesBySender(String username) {
        MessengerUser user = userRepository.findByUsername(username);

        return messageRepository.findBySender(user.getUuid());
    }

    public UserMessage removeReceivedMessage(UUID messageUuid, String username) {
        UserMessage message = messageRepository.findById(messageUuid).orElseThrow(() -> new MessageNotFoundException(messageUuid));
        if (!message.getRecipient().equals(userRepository.findByUsername(username).getUuid()))
            throw new UnauthorizedOperation(messageUuid, username);

        messageRepository.delete(message);
        return message;
    }
}

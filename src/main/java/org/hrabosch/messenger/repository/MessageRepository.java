package org.hrabosch.messenger.repository;

import java.util.UUID;

import org.hrabosch.messenger.entity.UserMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<UserMessage, UUID> {

}

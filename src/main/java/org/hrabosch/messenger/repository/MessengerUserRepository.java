package org.hrabosch.messenger.repository;

import java.util.UUID;

import org.hrabosch.messenger.entity.MessengerUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessengerUserRepository extends MongoRepository<MessengerUser, UUID> {

    MessengerUser findByUsername(String username);

}

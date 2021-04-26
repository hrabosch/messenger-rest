package org.hrabosch.messenger.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hrabosch.messenger.model.users.UserInfo;
import org.hrabosch.messenger.repository.MessengerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private MessengerUserRepository repository;

    @Autowired
    public UsersService(MessengerUserRepository repository) {
        this.repository = repository;
    }

    public List<UserInfo> getAllUsersInfo() {
        // TODO Mapstruct again..
        return repository.findAll()
                .stream()
                .map(messengerUser -> new UserInfo(messengerUser.getUuid(), messengerUser.getUsername()))
                .collect(Collectors.toList());
    }

}

package org.hrabosch.messenger.controller;

import java.util.List;

import org.hrabosch.messenger.model.users.UserInfo;
import org.hrabosch.messenger.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<UserInfo> listAllUsers() {
        return usersService.getAllUsersInfo();
    }
}

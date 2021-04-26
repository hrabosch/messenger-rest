package org.hrabosch.messenger.service;

import org.hrabosch.messenger.entity.MessengerUser;
import org.hrabosch.messenger.entity.MongoUserDetails;
import org.hrabosch.messenger.repository.MessengerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MessengerUserRepository repository;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MessengerUser user = repository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return MongoUserDetails.build(user);
    }
}

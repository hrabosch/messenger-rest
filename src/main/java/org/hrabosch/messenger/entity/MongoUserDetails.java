package org.hrabosch.messenger.entity;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = MongoUserDetails.class)
@Getter
public class MongoUserDetails implements UserDetails {

    private String username;
    private String password;

    public MongoUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MongoUserDetails(String username) {
        this.username = username;
    }

    public static UserDetails build(MessengerUser user) {
        return new MongoUserDetails(user.getUsername(), user.getPassword());
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }
}
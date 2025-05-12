package com.spidey.openmusicapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private String password;
    private List<String> permissions;
    private UserDO user;
    private String token;

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @NonNull
    public static LoginUserDetails of(@NonNull UserDO user) {
        LoginUserDetails userDetails = new LoginUserDetails();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setUser(user);
        return userDetails;
    }
}

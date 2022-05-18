package com.javamaster.config.jwt;

import com.javamaster.entities.User;
import com.javamaster.entities.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String name;
    private String login;
    private String password;
    private UserRole userRole;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(User userEntity) {
        CustomUserDetails c = new CustomUserDetails();
        c.id = userEntity.getId();
        c.name = userEntity.getName();
        c.login = userEntity.getEmail();
        c.password = userEntity.getPassword();
        c.userRole = userEntity.getRole();
        c.grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

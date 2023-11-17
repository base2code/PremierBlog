package de.base2code.blog.model;

import de.base2code.blog.dto.web.user.UserRegisterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;

    public User(UserRegisterDto userRegisterDto) {
        this.username = userRegisterDto.getUsername();
        this.email = userRegisterDto.getEmail();
        this.password = userRegisterDto.getPassword();
        this.enabled = true;
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

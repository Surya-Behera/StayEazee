package Authentication.SpringAuthJwt.Entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public enum Role {
    USER,
    ADMIN;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}

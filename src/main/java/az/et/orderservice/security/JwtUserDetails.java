package az.et.orderservice.security;

import az.et.orderservice.model.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JwtUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private Long id;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public JwtUserDetails() {
    }

    public JwtUserDetails(Long id, String username, String password, Collection<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static JwtUserDetails create(Long id, String username, String password, List<String> roles) {
        return new JwtUserDetails(
                id,
                username,
                password,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    public static JwtUserDetails of(Long id, String username, String password, List<RoleEntity> roles) {
        return new JwtUserDetails(
                id,
                username,
                password,
                roles.stream().map(RoleEntity::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }


}
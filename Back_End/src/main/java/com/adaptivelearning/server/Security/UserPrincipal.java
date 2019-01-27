package com.adaptivelearning.server.Security;

import com.adaptivelearning.server.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String gender;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;


    public UserPrincipal(Integer id, String name, String email, String password, int gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        if(gender == 1)
            this.gender = "male";
        else if(gender == 2)
            this.gender = "female";
    }

    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
//                new SimpleGrantedAuthority(role.getName().name())
//        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserId(),
                user.getFirstName() +" "+ user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender()
        );
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
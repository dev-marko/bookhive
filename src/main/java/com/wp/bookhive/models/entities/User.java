package com.wp.bookhive.models.entities;

import com.wp.bookhive.models.enums.AuthenticationType;
import com.wp.bookhive.models.enums.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "bookhive_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Roles role;

    private String name;
    private String surname;
    private int age;
    private String address;
    private String location;
    private String biography;
    private String interests;

    @OneToMany(mappedBy = "user")
    private List<UserBook> userBooks;

    @OneToMany
    @JoinTable(
            name = "my_wishlist",
            joinColumns = {@JoinColumn(name = "fk_user")},
            inverseJoinColumns = {@JoinColumn(name = "fk_book")}
    )
    private List<Book> wishlist;

    @ManyToMany(mappedBy = "members")
    private List<BookClub> bookClubs;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private AuthenticationType authType;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public User(String email, String password, Roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(this.role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}

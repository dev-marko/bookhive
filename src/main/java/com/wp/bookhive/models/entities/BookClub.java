package com.wp.bookhive.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn
    private User owner;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "bookClub", cascade = CascadeType.ALL)
    private List<Topic> topics;

    private String description;

    public BookClub(String name, User owner, String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.members = new ArrayList<>();
    }

    public boolean isMember(User user) {
        for (User u:
             members) {
            if (u.getId().equals(user.getId()))
                return true;
        }

        return false;
    }

}

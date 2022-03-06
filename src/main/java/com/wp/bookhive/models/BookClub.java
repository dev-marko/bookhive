package com.wp.bookhive.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class BookClub {
    @Id
    private String name;
    @ManyToMany
    private List<User> members;
    private String description;
}

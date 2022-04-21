package com.wp.bookhive.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private User sender;

    @ManyToOne
    @JoinColumn
    private User receiver;

    @ManyToOne
    @JoinColumn
    private BookClub bookClub;

    private String message;
}

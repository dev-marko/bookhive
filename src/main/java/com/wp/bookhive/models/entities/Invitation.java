package com.wp.bookhive.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private BookClub bookClub;

    private String message;

    private Boolean isRequest;

    public Invitation(User sender, User receiver, BookClub bookClub, String message, Boolean isRequest) {
        this.sender = sender;
        this.receiver = receiver;
        this.bookClub = bookClub;
        this.message = message;
        this.isRequest = isRequest;
    }
}

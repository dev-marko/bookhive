package com.wp.bookhive.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private BookClub bookClub;

    private String title;

    private LocalDate dateCreated;

    public Topic(User user, BookClub bookClub, String title) {
        this.user = user;
        this.bookClub = bookClub;
        this.title = title;
        this.dateCreated = LocalDate.now();
    }
}

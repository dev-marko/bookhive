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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Topic topic;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column(columnDefinition="TEXT")
    private String content;

    private LocalDate dateCreated;

    public Post(Topic topic, User user, String content) {
        this.topic = topic;
        this.user = user;
        this.content = content;
        this.dateCreated = LocalDate.now();
    }
}

package com.wp.bookhive.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Book {
    private String name;
    private String Description;
    @Id
    private String isbn;
    private LocalDateTime datePublished;
    @ManyToMany
    private List<Author> authors;


//    private Double price;
//    private Double discount;
//    treba da bidat vo relacijata pomegju book i bookshop

//    private long cursor
//    treba da bide vo relacijata pomegju book i user

}

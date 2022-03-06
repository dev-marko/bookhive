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
public class BookShop {
    @Id
    private int id;
    private String address;
    private String city;
    private String name;
    @ManyToMany
    private List<Book> books;
    private double latitude;
    private double longitude;
    private double grade;
    private int numGraders;
}

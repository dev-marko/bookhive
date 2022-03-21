package com.wp.bookhive.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private String city;
    private String name;
    private String bookshopEmail;
    private String phoneNumber;
    private String webSiteLink;

    @ManyToMany
    private List<Book> books;

    private double latitude;
    private double longitude;
    private double grade;
    private int numGraders;

}

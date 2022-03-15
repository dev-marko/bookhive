package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.BookShop;

import java.util.List;

public interface BookshopService {

    List<BookShop> getAllBookshops();
    BookShop findById(Integer bookshopId);
    BookShop edit(Integer bookshopId, String address, String city, String name, String bookshopEmail, String phoneNumber, String webSiteLink, String latitude, String longitude);
    BookShop save(String address, String city, String name, String bookshopEmail, String phoneNumber, String webSiteLink, String latitude, String longitude);
    void deleteById(int bookshopId);
}

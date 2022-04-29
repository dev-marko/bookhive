package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.BookShop;

import java.util.List;

public interface BookshopService {

    List<BookShop> getAllBookshops();

    List<BookShop> searchBookshops(String search);

    BookShop findById(Integer bookshopId);

    BookShop edit(Integer bookshopId, String address, String city, String name, String bookshopEmail, String phoneNumber, String webSiteLink, String latitude, String longitude, List<Integer> books);

    BookShop save(String address, String city, String name, String bookshopEmail, String phoneNumber, String webSiteLink, String latitude, String longitude, List<Integer> books);

    List<BookShop> findAllByBook(Book book);

    void deleteById(int bookshopId);
}

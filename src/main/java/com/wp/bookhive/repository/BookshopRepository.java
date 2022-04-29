package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.BookShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookshopRepository extends JpaRepository<BookShop,Integer> {
    List<BookShop> getAllByBooks(Book book);
    List<BookShop> findAllByNameContainingIgnoreCase(String containing);
}

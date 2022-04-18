package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.entities.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {
    List<UserBook> findByUser(User user);
    UserBook findByUserAndBook(User user, Book book);
}

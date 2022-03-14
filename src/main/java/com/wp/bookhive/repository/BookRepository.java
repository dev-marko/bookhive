package com.wp.bookhive.repository;

import com.wp.bookhive.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}

package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.enums.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
    Page<Book> findAllByIsValidTrue(Pageable pageable);
    List<Book> findAllByGenresIn(List<Genres> genres);
}

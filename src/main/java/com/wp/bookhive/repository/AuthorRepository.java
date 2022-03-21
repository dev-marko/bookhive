package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByNameOrSurnameContaining(String name, String surname);
}

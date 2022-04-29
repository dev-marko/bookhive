package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByNameOrSurnameContaining(String name, String surname);
}

package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}

package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookclubRepository extends JpaRepository<BookClub, Integer> {
    List<BookClub> findByMembers(User member);
}

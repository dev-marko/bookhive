package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.BookShop;
import com.wp.bookhive.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface BookclubRepository extends JpaRepository<BookClub, Integer> {
    List<BookClub> findByMembers(User member);
    List<BookClub> findAllByNameContainingIgnoreCase(String containing);
}

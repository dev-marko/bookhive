package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"booksOwned"})
    @Query("select u from User u")
    Optional<User> findByIdEagerBooks(Integer id);
}

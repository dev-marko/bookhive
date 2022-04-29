package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.enums.AuthenticationType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.authType = ?2 WHERE u.email = ?1")
    void updateAuthenticationType(String username, AuthenticationType authType);
}

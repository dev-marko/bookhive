package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findByBookClub(BookClub bookClub);
    Optional<Topic> findByTitle(String title);
}

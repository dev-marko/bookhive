package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.Post;
import com.wp.bookhive.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByTopic(Topic topic);

}

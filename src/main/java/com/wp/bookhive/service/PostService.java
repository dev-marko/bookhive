package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(Integer postId);

    List<Post> findByTopic(Integer topicId);

    Post save(String content, Integer userId, Integer topicId);

    Post edit(Integer postId, String content, Integer userId, Integer topicId);

    void deleteById(Integer postId);

}

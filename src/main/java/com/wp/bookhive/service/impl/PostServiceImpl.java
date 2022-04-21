package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Post;
import com.wp.bookhive.models.entities.Topic;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.PostNotFoundException;
import com.wp.bookhive.models.exceptions.TopicNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.PostRepository;
import com.wp.bookhive.repository.TopicRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Post findById(Integer postId) {
        return this.postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public List<Post> findByTopic(Integer topicId) {
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        return this.postRepository.findByTopic(topic);
    }

    @Override
    public Post save(String content, Integer userId, Integer topicId) {
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return this.postRepository.save(new Post(topic, user, content));
    }

    @Override
    public Post edit(Integer postId, String content, Integer userId, Integer topicId) {
        Post post = this.findById(postId);

        post.setContent(content);
        Topic topic = this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
        post.setTopic(topic);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        post.setUser(user);

        return this.postRepository.save(post);
    }

    @Override
    public void deleteById(Integer postId) {
        this.postRepository.deleteById(postId);
    }
}

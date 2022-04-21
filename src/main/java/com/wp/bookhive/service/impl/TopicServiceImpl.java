package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Topic;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.BookclubNotFoundException;
import com.wp.bookhive.models.exceptions.TopicNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.BookclubRepository;
import com.wp.bookhive.repository.TopicRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final BookclubRepository bookclubRepository;
    private final UserRepository userRepository;

    public TopicServiceImpl(TopicRepository topicRepository, BookclubRepository bookclubRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.bookclubRepository = bookclubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }

    @Override
    public Topic findById(Integer topicId) {
        return this.topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
    }

    @Override
    public List<Topic> findByBookClub(Integer bookClubId) {
        BookClub bookClub = this.bookclubRepository.findById(bookClubId).orElseThrow(() -> new BookclubNotFoundException(bookClubId));
        return this.topicRepository.findByBookClub(bookClub);
    }

    @Override
    public Topic save(String title, Integer userId, Integer bookClubId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        BookClub bookClub = this.bookclubRepository.findById(bookClubId).orElseThrow(() -> new BookclubNotFoundException(bookClubId));
        return this.topicRepository.save(new Topic(user, bookClub, title));
    }

    @Override
    public Topic edit(Integer topicId, String title, Integer userId, Integer bookClubId) {
        Topic topic = this.findById(topicId);

        topic.setTitle(title);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        topic.setUser(user);
        BookClub bookClub = this.bookclubRepository.findById(bookClubId).orElseThrow(() -> new BookclubNotFoundException(bookClubId));
        topic.setBookClub(bookClub);

        return this.topicRepository.save(topic);
    }

    @Override
    public void deleteById(Integer topicId) {
        this.topicRepository.deleteById(topicId);
    }
}

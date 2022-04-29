package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Topic;
import java.util.List;

public interface TopicService {
    List<Topic> findAll();
    Topic findById(Integer topicId);
    List<Topic> findByBookClub(Integer bookClubId);
    Topic save(String title, Integer userId, Integer bookClubId);
    Topic edit(Integer topicId, String title, Integer userId, Integer bookClubId);
    void deleteById(Integer topicId);
}

package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Publisher;

import java.util.List;

public interface PublisherService {

    List<Publisher> getAllPublishers();
    Publisher findById(Integer publisherId);
    Publisher edit(Integer publisherId, String name, String address, String email, String phoneNumber, String webSiteLink);
    Publisher save(String name, String address, String email, String phoneNumber, String webSiteLink);
    void deleteById(int publisherId);
}

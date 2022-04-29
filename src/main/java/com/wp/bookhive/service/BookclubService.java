package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.BookClub;

import java.util.List;

public interface BookclubService {

    List<BookClub> findAll();

    BookClub findById(Integer bookClubId);

    List<BookClub> findByMember(Integer userId);

    List<BookClub> findAllByNameContainingIgnoreCase(String containing);

    BookClub save(String name, Integer ownerId, String description);

    BookClub edit(Integer bookClubId, String name, Integer ownerId, String description);

    BookClub addUserToBookclub(Integer bookClubId, Integer userId);

    BookClub removeUserFromBookclub(Integer bookClubId, Integer userId);

    void deleteById(Integer bookClubId);
}

package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.BookclubNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.BookclubRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.BookclubService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookclubServiceImpl implements BookclubService {

    private final BookclubRepository bookclubRepository;
    private final UserRepository userRepository;

    @Override
    public List<BookClub> findAll() {
        return this.bookclubRepository.findAll();
    }

    @Override
    public BookClub findById(Integer bookClubId) {
        return this.bookclubRepository.findById(bookClubId).orElseThrow(() -> new BookclubNotFoundException(bookClubId));
    }

    @Override
    public List<BookClub> findByMember(Integer userId) {
        User member = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return this.bookclubRepository.findByMembers(member);
    }

    @Override
    public BookClub save(String name, Integer ownerId, String description) {
        User owner = this.userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId));
        BookClub bookClub = new BookClub(name, owner, description);
        bookClub.getMembers().add(owner);
        return this.bookclubRepository.save(bookClub);
    }

    @Override
    public BookClub edit(Integer bookClubId, String name, Integer ownerId, String description) {
        BookClub bookClub = this.findById(bookClubId);

        bookClub.setName(name);
        User owner = this.userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId));
        bookClub.setOwner(owner);
        bookClub.setDescription(description);

        return this.bookclubRepository.save(bookClub);
    }

    @Override
    public BookClub addUserToBookclub(Integer bookClubId, Integer userId) {
        BookClub bookClub = this.findById(bookClubId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        bookClub.getMembers().add(user);
        return this.bookclubRepository.save(bookClub);
    }

    @Override
    public BookClub removeUserFromBookclub(Integer bookClubId, Integer userId) {
        BookClub bookClub = this.findById(bookClubId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        bookClub.getMembers().remove(user);
        return this.bookclubRepository.save(bookClub);
    }

    @Override
    public void deleteById(Integer bookClubId) {
        this.bookclubRepository.deleteById(bookClubId);
    }
}

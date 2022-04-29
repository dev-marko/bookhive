package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Invitation;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.BookclubNotFoundException;
import com.wp.bookhive.models.exceptions.InvitationNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.BookclubRepository;
import com.wp.bookhive.repository.InvitationRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final BookclubRepository bookclubRepository;

    @Override
    public Invitation findById(Integer invitationId) {
        return this.invitationRepository.findById(invitationId).orElseThrow(() -> new InvitationNotFoundException(invitationId));
    }

    @Override
    public List<Invitation> findByReceiver(String receiverEmail) {
        User receiver = this.userRepository.findByEmail(receiverEmail).orElseThrow(() -> new UserNotFoundException(receiverEmail));
        return this.invitationRepository.findByReceiver(receiver);
    }

    @Override
    public List<Invitation> findByBookClubAndIsRequest(Integer bookClubId, Boolean isRequest) {
        BookClub bookClub = this.bookclubRepository.findById(bookClubId).orElseThrow(() -> new BookclubNotFoundException(bookClubId));
        return this.invitationRepository.findByBookClubAndIsRequest(bookClub, isRequest);
    }

    @Override
    public List<Invitation> findByReceiverAndIsRequest(Integer receiverId, Boolean isRequest) {
        User receiver = this.userRepository.findById(receiverId).orElseThrow(() -> new UserNotFoundException(receiverId));
        return this.invitationRepository.findByReceiverAndIsRequest(receiver, isRequest);
    }

    @Override
    public Invitation save(Integer senderId, String receiverEmail, Integer bookClubId, String message, Boolean isRequest) {
        User sender = this.userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = this.userRepository.findByEmail(receiverEmail).orElseThrow(() -> new UserNotFoundException(receiverEmail));
        BookClub bookClub = this.bookclubRepository.findById(bookClubId).orElseThrow(() -> new BookclubNotFoundException(bookClubId));
        return this.invitationRepository.save(new Invitation(sender, receiver, bookClub, message, isRequest));
    }

    @Override
    public void deleteById(Integer invitationId) {
        this.invitationRepository.deleteById(invitationId);
    }
}

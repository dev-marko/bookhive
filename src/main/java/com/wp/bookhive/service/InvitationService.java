package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Invitation;

import java.util.List;

public interface InvitationService {
    Invitation findById(Integer invitationId);

    List<Invitation> findByReceiver(String receiverEmail);

    List<Invitation> findByBookClubAndIsRequest(Integer bookClubId, Boolean isRequest);

    List<Invitation> findByReceiverAndIsRequest(Integer receiverId, Boolean isRequest);

    Invitation save(Integer senderId, String receiverEmail, Integer bookClubId, String message, Boolean isRequest);

    void deleteById(Integer invitationId);
}

package com.wp.bookhive.repository;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Invitation;
import com.wp.bookhive.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    List<Invitation> findByReceiver(User receiver);
    List<Invitation> findByBookClubAndIsRequest(BookClub bookClub, Boolean isRequest);
    List<Invitation> findByReceiverAndIsRequest(User receiver, Boolean isRequest);
}

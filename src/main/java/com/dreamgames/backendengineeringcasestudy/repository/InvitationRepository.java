package com.dreamgames.backendengineeringcasestudy.repository;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    boolean existsByInviterIdAndInvitedIdAndEvent(User inviterUser, User invitedUser, Event event);
}

package com.dreamgames.backendengineeringcasestudy.repository;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    //boolean existsByInviterIdAndInvitedIdAndEvent(User inviterUser, User invitedUser, Event event);

    //List<Invitation> findAllByInviterUserOrInvitedUser(User inviterUser, User InvitedUser);

    @Query("SELECT i FROM Invitation i WHERE (i.inviterUser = :user1 OR i.invitedUser = :user1) OR (i.inviterUser = :user2 OR i.invitedUser = :user2)")
    List<Invitation> findAllByInviterUserOrInvitedUser(@Param("user1") User user1, @Param("user2") User user2);


    List<Invitation> findInvitationByInviterUserOrInvitedUser(User inviterUser, User invitedUser);
}

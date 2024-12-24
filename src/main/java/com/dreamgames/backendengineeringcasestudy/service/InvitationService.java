package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dreamgames.backendengineeringcasestudy.model.Invitation.Status.*;

@Service
public class InvitationService {

    private final EventService eventService;

    private final PartnershipService partnershipService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final InvitationRepository invitationRepository;


    @Autowired
    public InvitationService(EventService eventService, PartnershipService partnershipService, UserService userService, UserRepository userRepository, InvitationRepository invitationRepository){
        this.eventService = eventService;
        this.partnershipService = partnershipService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }




    public void invitePartner(Long inviterId, Long invitedId, Event event) {
        if (!eventService.isEventActive(event)) {
            throw new RuntimeException("Event is not active");
        }

        Event activeEvent = eventService.getActiveEvent().orElseThrow(() -> new RuntimeException("no active event"));

        User inviter = userRepository.findById(inviterId).orElseThrow(() -> new RuntimeException("User with ID " + inviterId + " not found"));
        User invited = userRepository.findById(invitedId).orElseThrow(() -> new RuntimeException("User with ID " + invitedId + " not found"));

        if (inviter.getLevel() < 50 || invited.getLevel() < 50) {
            throw new RuntimeException("Both users must be at least level 50 to participate");
        }

        if(userService.hasPartner(inviterId)) {
            throw new RuntimeException("User with ID " + inviterId + " Already has a partner in the active event");
        }

        if(userService.hasPartner(invitedId)) {
            throw new RuntimeException("User with ID " + invitedId + " Already has a partner in the active event");
        }

        if(!inviter.getAbGroup().equals(invited.getAbGroup())) {
            throw new RuntimeException("Users must belong to the same group to partner");
        }

        Invitation newInvitation = new Invitation();

        newInvitation.setInvitedUser(invited);
        newInvitation.setInviterUser(inviter);
        newInvitation.setEvent(activeEvent);
        newInvitation.setAbGroup(inviter.getAbGroup());
        newInvitation.setStatus(PENDING);
        invitationRepository.save(newInvitation);

    }

    public void acceptInvitation(Long inviterId, Long invitationId, Event event) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new RuntimeException("No such invitation"));

        if (!eventService.isEventActive(event)) {
            invitation.setStatus(DEPRECATED);
            invitationRepository.save(invitation);
            throw new RuntimeException("Event is not active");
        }

        Long invitedId = invitation.getInvitedUser().getId();


        User inviter = userRepository.findById(inviterId).orElseThrow(() -> new RuntimeException("No inviter with ID: " + inviterId));
        User invited = userRepository.findById(invitedId).orElseThrow(() -> new RuntimeException("No invited user with ID: " + invitedId));

        // Deprecate all other invitations for both inviter and invited users
        List<Invitation> otherInvitations = invitationRepository.findAllByInviterUserOrInvitedUser(inviter, invited);
        for (Invitation otherInvitation : otherInvitations ) {
            if (!otherInvitation.getId().equals(invitationId) && otherInvitation.getStatus() == PENDING) {
                otherInvitation.setStatus(DEPRECATED);
            }
        }
        invitationRepository.saveAll(otherInvitations);


        partnershipService.createPartnership(inviter, invited, event);

        invitation.setStatus(ACCEPTED);
        invitationRepository.save(invitation);

    }

    public void rejectInvitation (Long invitationId) {

        // Retrieve the invitation by its ID
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("No invitation found with ID: " + invitationId));

        // Check the current status of the invitation
        if (invitation.getStatus() == REJECTED) {
            throw new RuntimeException("Invitation is already rejected");
        }

        else if (invitation.getStatus() == DEPRECATED) {
            throw new RuntimeException("Invitation is deprecated");
        }

        else if (invitation.getStatus() == ACCEPTED) {
            throw new RuntimeException("Invitation is accepted");
        }

        // Update the status to REJECTED
        invitation.setStatus(REJECTED);

        // Save the updated invitation
        invitationRepository.save(invitation);

    }

    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    public List<Invitation> findInvitationsForUser(User user1, User user2) {
        return invitationRepository.findInvitationByInviterUserOrInvitedUser(user1, user2);
    }

    public List<Invitation> getReceivedInvitations(User user, Event event) {
        return invitationRepository.findReceivedInvitationByUserAndEvent(user, event, Invitation.Status.PENDING);
    }
}

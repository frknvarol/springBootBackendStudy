package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.dto.dto.InvitationDTO;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.InvitePartnerRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitePartnerResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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




    public InvitePartnerResponse invitePartner(InvitePartnerRequest request) {
        Event activeEvent = eventService.getActiveEvent().orElseThrow(() -> new RuntimeException("no active event"));


        User inviter = userRepository.findById(request.getInviterId())
                .orElseThrow(() -> new RuntimeException("User with ID " + request.getInviterId() + " not found"));
        User invited = userRepository.findById(request.getInvitedId())
                .orElseThrow(() -> new RuntimeException("User with ID " + request.getInvitedId() + " not found"));

        if (inviter.getLevel() < 50 || invited.getLevel() < 50) {
            throw new RuntimeException("Both users must be at least level 50 to participate");
        }

        if(userService.hasPartner(request.getInviterId())) {
            throw new RuntimeException("User with ID " + request.getInviterId() + " Already has a partner in the active event");
        }

        if(userService.hasPartner(request.getInvitedId())) {
            throw new RuntimeException("User with ID " + request.getInvitedId() + " Already has a partner in the active event");
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

        return new InvitePartnerResponse("Invitation sent successfully", newInvitation.getId());

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

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("No invitation found with ID: " + invitationId));

        if (invitation.getStatus() == REJECTED) {
            throw new RuntimeException("Invitation is already rejected");
        }

        else if (invitation.getStatus() == DEPRECATED) {
            throw new RuntimeException("Invitation is deprecated");
        }

        else if (invitation.getStatus() == ACCEPTED) {
            throw new RuntimeException("Invitation is accepted");
        }

        invitation.setStatus(REJECTED);

        invitationRepository.save(invitation);

    }

    public List<Invitation> getInvitations() {
        return invitationRepository.findAll();
    }

    public List<Invitation> findInvitationsForUser(User user1, User user2) {
        return invitationRepository.findInvitationByInviterUserOrInvitedUser(user1, user2);
    }

    public InvitationsResponse getReceivedInvitations(GetInvitationsRequest request) {

        User invitedUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event currentEvent = eventService.getActiveEvent()
                .orElseThrow(() -> new RuntimeException("No active event"));

        List<Invitation> invitations = invitationRepository.findReceivedInvitationByUserAndEvent(
                invitedUser, currentEvent, Invitation.Status.PENDING
        );

        List<InvitationDTO> invitationDTOs = invitations.stream()
                .map(invitation -> new InvitationDTO(
                        invitation.getId(),
                        invitation.getInviterUser().getId(),
                        invitation.getInviterUser().getUsername()
                ))
                .toList();

        return new InvitationsResponse(invitationDTOs);
    }

    public List<Invitation> getSentInvitations(User inviterUser) {
        Event currentEvent = eventService.getActiveEvent().orElseThrow(() -> new RuntimeException("no active event"));
        return invitationRepository.findSentInvitationByUserAndEvent(inviterUser, currentEvent, Invitation.Status.PENDING);
    }


}

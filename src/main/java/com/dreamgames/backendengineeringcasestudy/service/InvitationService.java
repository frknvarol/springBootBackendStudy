package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.dto.dto.InvitationDTO;
import com.dreamgames.backendengineeringcasestudy.dto.request.AcceptInvitationRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.InvitePartnerRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.RejectInvitationRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.AcceptInvitationResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetInvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitePartnerResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.RejectInvitationResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.EventRepository;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.dreamgames.backendengineeringcasestudy.model.Invitation.Status.*;

@Service
public class InvitationService {

    private final EventService eventService;

    private final PartnershipService partnershipService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final InvitationRepository invitationRepository;

    private final EventRepository eventRepository;


    @Autowired
    public InvitationService(EventService eventService, PartnershipService partnershipService, UserService userService, UserRepository userRepository, InvitationRepository invitationRepository, EventRepository eventRepository){
        this.eventService = eventService;
        this.partnershipService = partnershipService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.eventRepository = eventRepository;
    }




    public InvitePartnerResponse invitePartner(InvitePartnerRequest request) {
        Event activeEvent = eventService.getActiveEvent().orElseThrow(() -> new RuntimeException("no active event"));




        User inviterUser = userRepository.findById(request.getInviterId())
                .orElseThrow(() -> new RuntimeException("User with ID " + request.getInviterId() + " not found"));
        User invitedUser = userRepository.findById(request.getInvitedId())
                .orElseThrow(() -> new RuntimeException("User with ID " + request.getInvitedId() + " not found"));

        // checks whether the inviter user has already been rejected by the invited user
        List<Invitation> rejectedInvitations = invitationRepository.findReceivedInvitationByUserAndEvent(
                invitedUser, activeEvent, Invitation.Status.REJECTED
        );

        for (Invitation invitation : rejectedInvitations) {
            if (Objects.equals(invitation.getInviterUser().getId(), request.getInviterId())) {
                throw new RuntimeException("Rejected users cannot send another invitation during the same event");
            }
        }

        if (inviterUser.getLevel() < 50 || invitedUser.getLevel() < 50) {
            throw new RuntimeException("Both users must be at least level 50 to participate");
        }

        if(userService.hasPartner(request.getInviterId())) {
            throw new RuntimeException("User with ID " + request.getInviterId() + " Already has a partner in the active event");
        }

        if(userService.hasPartner(request.getInvitedId())) {
            throw new RuntimeException("User with ID " + request.getInvitedId() + " Already has a partner in the active event");
        }

        if(!inviterUser.getAbGroup().equals(invitedUser.getAbGroup())) {
            throw new RuntimeException("Users must belong to the same group to partner");
        }

        Invitation newInvitation = new Invitation();

        newInvitation.setInvitedUser(invitedUser);
        newInvitation.setInviterUser(inviterUser);
        newInvitation.setEvent(activeEvent);
        newInvitation.setAbGroup(inviterUser.getAbGroup());
        newInvitation.setStatus(PENDING);
        invitationRepository.save(newInvitation);

        return new InvitePartnerResponse("Invitation sent successfully", newInvitation.getId());

    }

    @Transactional
    public AcceptInvitationResponse acceptInvitation(AcceptInvitationRequest request) {
        Invitation invitation = invitationRepository.findById(request.getInvitationId()).orElseThrow(() -> new RuntimeException("No such invitation"));

        Long inviterId = request.getInviterId();
        Long invitedId = invitation.getInvitedUser().getId();

        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new RuntimeException("no such event"));


        if (invitation.getStatus() != PENDING) {
            throw new RuntimeException("Event is not active");
        }



        User inviterUser = userRepository.findById(inviterId).orElseThrow(() -> new RuntimeException("No inviter with ID: " + inviterId));
        User invitedUser = userRepository.findById(invitedId).orElseThrow(() -> new RuntimeException("No invited user with ID: " + invitedId));

        // deprecates all other invitations that belong to both of the users
        List<Invitation> otherInvitations = invitationRepository.findAllByInviterUserOrInvitedUser(inviterUser, invitedUser);
        for (Invitation otherInvitation : otherInvitations ) {
            if (!otherInvitation.getId().equals(request.getInvitationId()) && otherInvitation.getStatus() == PENDING) {
                otherInvitation.setStatus(DEPRECATED);
            }
        }
        invitationRepository.saveAll(otherInvitations);


        partnershipService.createPartnership(inviterUser, invitedUser, event);

        invitation.setStatus(ACCEPTED);
        invitationRepository.save(invitation);

        return new AcceptInvitationResponse(request.getInvitationId(), "Invitation accepted");

    }

    public RejectInvitationResponse rejectInvitation (RejectInvitationRequest request) {

        Invitation invitation = invitationRepository.findById(request.getInvitationId())
                .orElseThrow(() -> new RuntimeException("No invitation found with ID: " + request.getInvitationId()));

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

        return new RejectInvitationResponse(request.getInvitationId(), "Invitation rejected");

    }


    public GetInvitationsResponse getReceivedInvitations(GetInvitationsRequest request) {

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

        return new GetInvitationsResponse(invitationDTOs);
    }


}

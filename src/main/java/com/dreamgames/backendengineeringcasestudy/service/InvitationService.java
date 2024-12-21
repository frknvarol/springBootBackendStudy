package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {

    private final EventService eventService;

    private final PartnershipService partnershipService;

    @Autowired
    public InvitationService(EventService eventService, PartnershipService partnershipService){
        this.eventService = eventService;
        this.partnershipService = partnershipService;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

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

        if(partnershipService.hasPartner(inviterId)) {
            throw new RuntimeException("User with ID " + inviterId + "Already has a partner in the active event");
        }

        if(partnershipService.hasPartner(invitedId)) {
            throw new RuntimeException("User with ID " + invitedId + "Already has a partner in the active event");
        }

        if(!inviter.getAbGroup().equals(invited.getAbGroup())) {
            throw new RuntimeException("Users must belong to the same group to partner");
        }

        Invitation newInvitation = new Invitation();

        newInvitation.setInvitedId(invited);
        newInvitation.setInviterId(inviter);
        newInvitation.setEvent(activeEvent);
        newInvitation.setAbGroup(inviter.getAbGroup());
        newInvitation.setValid(true);
        invitationRepository.save(newInvitation);

    }

    public void acceptInvitation(Long inviterId, Long invitationId, Event event) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new RuntimeException("No such invitation"));

        if (!eventService.isEventActive(event)) {
            invitation.setValid(false);
            throw new RuntimeException("Event is not active");
        }

        Long invitedId = invitation.getInvitedId().getId();

        User inviter = userRepository.findById(inviterId).orElseThrow(() -> new RuntimeException("No inviter with ID: " + inviterId));
        User invited = userRepository.findById(invitedId).orElseThrow(() -> new RuntimeException("No inviter with ID: " + invitedId));

        partnershipService.createPartnership(inviter, invited, event);

    }
}

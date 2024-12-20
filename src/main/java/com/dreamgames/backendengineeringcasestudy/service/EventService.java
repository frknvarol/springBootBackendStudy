package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.EventRepository;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EventRepository eventRepository;

    public Optional<Event> getActiveEvent() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findActiveEvent(now);
    }

    private boolean isEventActive() {
        if(getActiveEvent().isEmpty()) {
            throw new RuntimeException("There is no active event.");
        }

        /*
        if (!event.equals(getActiveEvent().get())) {
            throw new RuntimeException("This event is not active");
        }
        */


        // current time in UTC
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneId.of("UTC"));
        LocalTime currentTime = utcTime.toLocalTime();

        return currentTime.isAfter(LocalTime.of(8,0)) && currentTime.isAfter(LocalTime.of(22, 0));
    }

    public void invitePartner(Long inviterId, Long invitedId) {
        if (!isEventActive()) {
            throw new RuntimeException("Event is not active");
        }

        Event activeEvent = getActiveEvent().orElseThrow(() -> new RuntimeException("no active event"));

        User inviter = userRepository.findById(inviterId).orElseThrow(() -> new RuntimeException("User with ID " + inviterId + " not found"));
        User invited = userRepository.findById(invitedId).orElseThrow(() -> new RuntimeException("User with ID " + invitedId + " not found"));

        if (inviter.getLevel() < 50 || invited.getLevel() < 50) {
            throw new RuntimeException("Both users must be at least level 50 to participate");
        }

        if(hasPartner(inviterId)) {
            throw new RuntimeException("User with ID " + inviterId + "Already has a partner in the active event");
        }

        if(hasPartner(invitedId)) {
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

        invitationRepository.save(newInvitation);

    }

    public void acceptInvitation(Long invitedId, Long invitationId, Event event) {

    }

    public boolean hasPartner(Long userId) {
        Optional<Partnership> partnershipOptional = partnershipRepository.findByUser1IdOrUser2Id(userId, userId);
        if(partnershipOptional.isEmpty()) {
            return false;
        }

        Partnership partnership = partnershipOptional.get();

        Event activeEvent = getActiveEvent().orElseThrow(() -> new RuntimeException("No active event"));


        return Objects.equals(partnership.getEvent().getId(), activeEvent.getId());


    }


}

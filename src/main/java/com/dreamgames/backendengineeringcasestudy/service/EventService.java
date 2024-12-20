package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
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

        User inviter = userRepository.findById(inviterId).orElseThrow(() -> new RuntimeException("invalid inviter ID"));
        User invited = userRepository.findById(invitedId).orElseThrow(() -> new RuntimeException("invalid ID"));

        if(inviter.getAbGroup() == invited.getAbGroup()) {

            Invitation newInvitation = new Invitation();

            newInvitation.setInvitedId(invited);
            newInvitation.setInviterId(inviter);
            newInvitation.setEvent(activeEvent);
            newInvitation.setAbGroup(inviter.getAbGroup());

            invitationRepository.save(newInvitation);

        }
    }
}

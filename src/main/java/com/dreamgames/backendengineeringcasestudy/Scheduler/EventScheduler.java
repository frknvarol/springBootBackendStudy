package com.dreamgames.backendengineeringcasestudy.Scheduler;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.repository.EventRepository;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class EventScheduler {

    private final EventRepository eventRepository;
    private final InvitationRepository invitationRepository;
    private final PartnershipRepository partnershipRepository;

    public EventScheduler(EventRepository eventRepository, PartnershipRepository partnershipRepository, InvitationRepository invitationRepository) {
        this.eventRepository = eventRepository;
        this.invitationRepository = invitationRepository;
        this.partnershipRepository = partnershipRepository;
    }

    // automatically adds an event to the table at 8:00
    @Scheduled(cron = "0 0 8 * * ?", zone = "UTC")
    public void createEvent() {
        Event newEvent = new Event();
        newEvent.setName("Event");
        newEvent.setStartTime(LocalDateTime.now());
        newEvent.setEndTime(LocalDateTime.now().plusHours(14));

        eventRepository.save(newEvent);
    }

    // deprecates the invitations that belong to an inactive event
    @Scheduled(cron = "0 0 22 * * ?", zone = "UTC")
    @Transactional
    public void deprecateInactiveInvitations() {
        invitationRepository.updateStatusForPending(Invitation.Status.DEPRECATED,  Invitation.Status.PENDING);
    }

    // deprecates the partnerships that belong to an inactive event
    @Scheduled(cron = "0 0 22 * * ?", zone = "UTC")
    @Transactional
    public void deprecateInactivePartnerships() {
        partnershipRepository.updateActiveForDeprecatedPartnerships(false, true);
    }
}
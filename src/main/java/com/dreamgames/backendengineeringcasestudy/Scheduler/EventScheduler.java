package com.dreamgames.backendengineeringcasestudy.Scheduler;
import com.dreamgames.backendengineeringcasestudy.Config.EventConfig;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.repository.EventRepository;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.service.EventService;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static com.dreamgames.backendengineeringcasestudy.model.Invitation.Status.DEPRECATED;
import static com.dreamgames.backendengineeringcasestudy.model.Invitation.Status.PENDING;

@Component
public class EventScheduler {

    private final EventRepository eventRepository;
    private final InvitationService invitationService;
    private final InvitationRepository invitationRepository;

    public EventScheduler(EventRepository eventRepository, InvitationService invitationService, InvitationRepository invitationRepository) {
        this.eventRepository = eventRepository;
        this.invitationService = invitationService;
        this.invitationRepository = invitationRepository;
    }

    @Scheduled(cron = "0 26 22 * * ?", zone = "UTC") // At 08:00 UTC daily
    public void createEvent() {
        Event newEvent = new Event();
        newEvent.setName("Event");
        newEvent.setStartTime(LocalDateTime.now());
        newEvent.setEndTime(LocalDateTime.now().plusHours(14));

        eventRepository.save(newEvent);
    }

    @Scheduled(cron = "0 47 22 * * ?", zone = "UTC") // At 22:00 UTC daily
    @Transactional
    public void deprecateInactiveInvitations() {

        //All invitations that belong to an inactive event automatically set to DEPRECATED
        /*
        List<Invitation> invitations = invitationService.getAllInvitations();
        for (Invitation invitation : invitations) {
            System.out.println("Before Update: " + invitation.getId() + " Status: " + invitation.getStatus());
            if (invitation.getStatus() == PENDING) {
                invitation.setStatus(DEPRECATED);
                invitationRepository.save(invitation);
                System.out.println("Updated: " + invitation.getId() + " Status: " + invitation.getStatus());
            }
        }
        */

        invitationRepository.updateStatusForPending( Invitation.Status.DEPRECATED,  Invitation.Status.PENDING);



    }
}
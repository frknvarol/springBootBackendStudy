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

        return eventRepository.findActiveEvent(LocalDateTime.now());
    }

    public boolean isEventActive(Event event) {
        if(getActiveEvent().isEmpty()) {
            throw new RuntimeException("There is no active event.");
        }


        else if (!event.getId().equals(getActiveEvent().get().getId())) {
            throw new RuntimeException("This event is not active");
        }

        return true;
    }






}

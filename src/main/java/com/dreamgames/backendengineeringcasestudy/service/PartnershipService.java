package com.dreamgames.backendengineeringcasestudy.service;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PartnershipService {

    private final EventService eventService;

    private final PartnershipRepository partnershipRepository;

    @Autowired
    public PartnershipService(EventService eventService, PartnershipRepository partnershipRepository) {
        this.eventService = eventService;
        this.partnershipRepository = partnershipRepository;
    }


    public void createPartnership(User inviter, User invited, Event event) {

        boolean partnershipExists = partnershipRepository.existsByUser1AndUser2AndEvent(inviter, invited, event);
        if (partnershipExists) {
            throw new RuntimeException("Partnership already exists between these users for this event");
        }

        Partnership newPartnership = new Partnership();

        newPartnership.setUser1(inviter);
        newPartnership.setUser2(invited);
        newPartnership.setEvent(eventService.getActiveEvent().orElseThrow(() -> new RuntimeException("no active event")));

        partnershipRepository.save(newPartnership);
    }




}

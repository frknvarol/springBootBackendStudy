package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PartnershipService {

    private final EventService eventService;

    @Autowired
    public PartnershipService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    private PartnershipRepository partnershipRepository;



    public boolean hasPartner(Long userId) {
        Long activeEventId = eventService.getActiveEvent()
                .orElseThrow(() -> new RuntimeException("No active event"))
                .getId();

        return partnershipRepository.existsByUserIdAndEventId(userId, activeEventId);
    }
}

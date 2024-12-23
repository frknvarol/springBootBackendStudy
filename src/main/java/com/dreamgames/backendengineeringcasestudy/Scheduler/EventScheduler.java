package com.dreamgames.backendengineeringcasestudy.Scheduler;
import com.dreamgames.backendengineeringcasestudy.Config.EventConfig;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.repository.EventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
public class EventScheduler {

    private final EventRepository eventRepository;

    public EventScheduler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "0 0 8 * * ?", zone = "UTC") // At 08:00 UTC daily
    public void endEvent() {
        Event newEvent = new Event();
        newEvent.setName("Event");
        newEvent.setStartTime(LocalDateTime.now());
        newEvent.setEndTime(LocalDateTime.now().plusHours(14));

        eventRepository.save(newEvent);
    }
}
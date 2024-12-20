package com.dreamgames.backendengineeringcasestudy;

import com.dreamgames.backendengineeringcasestudy.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Test
    public void testInvitePartner() {
        try {
            eventService.invitePartner(1L, 2L);
            System.out.println("Invitation sent successfully");
        } catch (RuntimeException e) {
            System.err.println("Failed to send invitation: " + e.getMessage());
        }
    }

}

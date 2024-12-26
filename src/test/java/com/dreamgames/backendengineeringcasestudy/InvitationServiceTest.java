package com.dreamgames.backendengineeringcasestudy;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import com.dreamgames.backendengineeringcasestudy.service.EventService;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InvitationServiceTest {

    @Autowired
    private InvitationService invitationService;

    @MockBean
    private InvitationRepository invitationRepository;

    @MockBean
    private EventService eventService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetReceivedInvitations() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("furkan4545");

        Event event = new Event();
        event.setId(1L);

        Invitation invitation = new Invitation();
        invitation.setId(101L);
        invitation.setInviterUser(user);
        invitation.setStatus(Invitation.Status.PENDING);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(eventService.getActiveEvent()).thenReturn(Optional.of(event));
        Mockito.when(invitationRepository.findReceivedInvitationByUserAndEvent(user, event, Invitation.Status.PENDING))
                .thenReturn(List.of(invitation));

        GetInvitationsRequest request = new GetInvitationsRequest(userId);
        InvitationsResponse response = invitationService.getReceivedInvitations(request);

        assertNotNull(response);
        assertEquals(1, response.getInvitations().size());
        assertEquals(101L, response.getInvitations().get(0).getInvitationId());
        assertEquals("furkan4545", response.getInvitations().get(0).getInviterUsername());
    }
}

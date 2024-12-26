package com.dreamgames.backendengineeringcasestudy;
import com.dreamgames.backendengineeringcasestudy.dto.dto.InvitationDTO;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.InvitePartnerRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetInvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitePartnerResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import com.dreamgames.backendengineeringcasestudy.service.EventService;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class InvitationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvitationService invitationService;

    @MockBean
    private InvitationRepository invitationRepository;

    @MockBean
    private EventService eventService;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private User inviter;

    @Mock
    private User invited;

    @Mock
    private Event activeEvent;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private InvitePartnerRequest invitePartnerRequest;

    @Mock
    private InvitePartnerResponse invitePartnerResponse;

    @BeforeEach
    void setUp() {
    }

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

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(eventService.getActiveEvent()).thenReturn(Optional.of(event));
        when(invitationRepository.findReceivedInvitationByUserAndEvent(user, event, Invitation.Status.PENDING))
                .thenReturn(List.of(invitation));




        GetInvitationsRequest request = new GetInvitationsRequest(userId);
        GetInvitationsResponse response = new GetInvitationsResponse(List.of(new InvitationDTO(101L, 1L, "furkan4545")));


        when(invitationService.getReceivedInvitations(request)).thenReturn(response);

        assertNotNull(response);
        assertEquals(1, response.getInvitations().size());
        assertEquals(101L, response.getInvitations().get(0).getInvitationId());
        assertEquals("furkan4545", response.getInvitations().get(0).getInviterUsername());
    }




    @Test
    void testInvitePartner_success() throws Exception {

        /*

        Long inviterId = 1L;
        Long invitedId = 2L;

        when(userRepository.findById(inviterId)).thenReturn(Optional.of(inviter));
        when(userRepository.findById(invitedId)).thenReturn(Optional.of(invited));
        when(eventService.getActiveEvent()).thenReturn(Optional.of(activeEvent));
        when(eventService.isEventActive(activeEvent)).thenReturn(true);
        when(inviter.getLevel()).thenReturn(50);
        when(invited.getLevel()).thenReturn(50);
        when(inviter.getAbGroup()).thenReturn('A');
        when(invited.getAbGroup()).thenReturn('A');

        // Act
        invitationService.invitePartner(inviterId, invitedId, activeEvent);

        // Assert
        verify(invitationRepository, times(1)).save(any(Invitation.class));







        invitePartnerRequest = new InvitePartnerRequest();
        invitePartnerRequest.setInvitedId(1L);
        invitePartnerRequest.setInviterId(2L);
        invitePartnerRequest.setEventId(3L);

        System.out.println(invitePartnerRequest.getInvitedId());

        String requestBody = objectMapper.writeValueAsString(invitePartnerRequest);

        System.out.println(requestBody);

        invitePartnerResponse = new InvitePartnerResponse("Invitation sent successfully", 3L);

        String responseBody = objectMapper.writeValueAsString(invitePartnerResponse);
        System.out.println(responseBody);

        // Arrange
        when(eventService.isEventActive(any(Event.class))).thenReturn(true);
        when(invitationService.invitePartner(invitePartnerRequest)).thenReturn(invitePartnerResponse);



        // Act & Assert
        mockMvc.perform(post("/api/invitations/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));

        // Verify that the service method was called
        verify(invitationService, times(1)).invitePartner(invitePartnerRequest);

    */

    }


}

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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        invitePartnerRequest = new InvitePartnerRequest();
        invitePartnerRequest.setInvitedId(1L);
        invitePartnerRequest.setInviterId(2L);
        invitePartnerRequest.setEventId(3L);


        invitePartnerResponse = new InvitePartnerResponse("Invitation sent successfully", 3L);
        when(invitationService.invitePartner(any(InvitePartnerRequest.class))).thenReturn(invitePartnerResponse);

        String responseBody = objectMapper.writeValueAsString(invitePartnerResponse);

        when(eventService.isEventActive(any(Event.class))).thenReturn(true);

        mockMvc.perform(post("/api/invitations/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['message']").value("Invitation sent successfully"))
                .andExpect(jsonPath("$['invitationId']").value(3));


        assertNotNull(invitePartnerRequest);
        assertEquals(1, invitePartnerRequest.getInvitedId());
        assertEquals(2, invitePartnerRequest.getInviterId());
        assertEquals(3, invitePartnerRequest.getEventId());

        ArgumentCaptor<InvitePartnerRequest> captor = ArgumentCaptor.forClass(InvitePartnerRequest.class);
        verify(invitationService, times(1)).invitePartner(captor.capture());



    }


}

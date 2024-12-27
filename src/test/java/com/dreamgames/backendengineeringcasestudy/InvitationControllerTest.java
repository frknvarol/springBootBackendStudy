package com.dreamgames.backendengineeringcasestudy;
import com.dreamgames.backendengineeringcasestudy.dto.dto.InvitationDTO;
import com.dreamgames.backendengineeringcasestudy.dto.request.*;
import com.dreamgames.backendengineeringcasestudy.dto.response.AcceptInvitationResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetInvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitePartnerResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.RejectInvitationResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import com.dreamgames.backendengineeringcasestudy.service.EventService;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private InvitePartnerRequest invitePartnerRequest;

    @Mock
    private InvitePartnerResponse invitePartnerResponse;

    @Mock
    private AcceptInvitationRequest acceptInvitationRequest;

    @Mock
    private AcceptInvitationResponse acceptInvitationResponse;

    @Mock
    private RejectInvitationRequest rejectInvitationRequest;

    @Mock
    private RejectInvitationResponse rejectInvitationResponse;

    @Mock
    private  GetInvitationsRequest getInvitationsRequest;

    @Mock
    private  GetInvitationsResponse getInvitationsResponse;


    @BeforeEach
    void setUp() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            objectMapper = new ObjectMapper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetReceivedInvitations() throws Exception {

        getInvitationsRequest = new GetInvitationsRequest();
        getInvitationsRequest.setUserId(1L);

        getInvitationsResponse = new GetInvitationsResponse();

        InvitationDTO invitation1 = new InvitationDTO(3L, 5L, "frkn");
        InvitationDTO invitation2 = new InvitationDTO(5L, 52L, "ahmet");
        InvitationDTO invitation3 = new InvitationDTO(7L, 15L, "furkan");

        getInvitationsResponse.setInvitations(Arrays.asList(invitation1, invitation2, invitation3));


        String requestBody = objectMapper.writeValueAsString(getInvitationsRequest);


        when(invitationService.getReceivedInvitations(any(GetInvitationsRequest.class))).thenReturn(getInvitationsResponse);

        mockMvc.perform(get("/invitations/received")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invitations[0].invitationId").value(3))
                .andExpect(jsonPath("$.invitations[0].inviterId").value(5))
                .andExpect(jsonPath("$.invitations[0].inviterUsername").value("frkn"))
                .andExpect(jsonPath("$.invitations[1].invitationId").value(5))
                .andExpect(jsonPath("$.invitations[1].inviterId").value(52))
                .andExpect(jsonPath("$.invitations[1].inviterUsername").value("ahmet"))
                .andExpect(jsonPath("$.invitations[2].invitationId").value(7))
                .andExpect(jsonPath("$.invitations[2].inviterId").value(15))
                .andExpect(jsonPath("$.invitations[2].inviterUsername").value("furkan"));





    }




    @Test
    void testInvitePartner() throws Exception {

        invitePartnerRequest = new InvitePartnerRequest();
        invitePartnerRequest.setInvitedId(1L);
        invitePartnerRequest.setInviterId(2L);
        invitePartnerRequest.setEventId(3L);


        invitePartnerResponse = new InvitePartnerResponse("Invitation sent successfully", 3L);
        when(invitationService.invitePartner(any(InvitePartnerRequest.class))).thenReturn(invitePartnerResponse);

        String responseBody = objectMapper.writeValueAsString(invitePartnerResponse);

        when(eventService.isEventActive(any(Event.class))).thenReturn(true);

        mockMvc.perform(post("/invitations/invite")
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

    @Test
    void testAcceptInvitation() throws Exception {
        acceptInvitationRequest = new AcceptInvitationRequest();
        acceptInvitationRequest.setInvitationId(1L);
        acceptInvitationRequest.setInviterId(2L);
        acceptInvitationRequest.setEventId(3L);

        acceptInvitationResponse = new AcceptInvitationResponse();
        acceptInvitationResponse.setMessage("Invitation accepted successfully");
        acceptInvitationResponse.setInvitationId(1L);

        when(invitationService.acceptInvitation(any(AcceptInvitationRequest.class))).thenReturn(acceptInvitationResponse);

        String responseBody = objectMapper.writeValueAsString(acceptInvitationResponse);

        // Perform the POST request
        mockMvc.perform(post("/invitations/accept-invitation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseBody))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(jsonPath("$.message").value("Invitation accepted successfully")) // Check the message
                .andExpect(jsonPath("$.invitationId").value(1)); // Check the invitation ID



    }

    @Test
    void testRejectInvitation() throws Exception {
        rejectInvitationRequest = new RejectInvitationRequest();
        rejectInvitationRequest.setInvitationId(1L);

        rejectInvitationResponse = new RejectInvitationResponse(1L,"Invitation rejected successfully");

        when(invitationService.rejectInvitation(any(RejectInvitationRequest.class))).thenReturn(rejectInvitationResponse);

        String responseBody = new ObjectMapper().writeValueAsString(rejectInvitationResponse);

        mockMvc.perform(post("/invitations/reject-invitation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Invitation rejected successfully"))
                .andExpect(jsonPath("$.invitationId").value(1));

    }

}

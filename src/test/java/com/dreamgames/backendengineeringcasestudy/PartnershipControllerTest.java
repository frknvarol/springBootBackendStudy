package com.dreamgames.backendengineeringcasestudy;

import com.dreamgames.backendengineeringcasestudy.dto.request.ClaimRewardRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetBalloonsInfoRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateBalloonProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.ClaimRewardResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetBalloonsInfoResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateBalloonProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.service.PartnershipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PartnershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartnershipService partnershipService;

    @MockBean
    private PartnershipRepository partnershipRepository;

    @Mock
    private ClaimRewardRequest claimRewardRequest;

    @Mock
    private ClaimRewardResponse claimRewardResponse;

    @Mock
    private Partnership partnership;

    @Mock
    private UpdateBalloonProgressRequest updateBalloonProgressRequest;

    @Mock
    private UpdateBalloonProgressResponse updateBalloonProgressResponse;

    @Mock
    private GetBalloonsInfoRequest getBalloonsInfoRequest;

    @Mock
    private GetBalloonsInfoResponse getBalloonsInfoResponse;


    @BeforeEach
    void setUp() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            objectMapper = new ObjectMapper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClaimReward() throws Exception {

        claimRewardRequest = new ClaimRewardRequest();
        claimRewardRequest.setPartnershipId(1L);

        claimRewardResponse = new ClaimRewardResponse();
        claimRewardResponse.setMessage("Reward claimed successfully");

        partnership = new Partnership();
        partnership.setId(1L);

        when(partnershipRepository.findById(claimRewardRequest.getPartnershipId())).thenReturn(java.util.Optional.of(partnership));

        String requestBody = objectMapper.writeValueAsString(claimRewardRequest);

        when(partnershipService.claimReward(any(ClaimRewardRequest.class))).thenReturn(claimRewardResponse);

        mockMvc.perform(post("/partnership/claim-reward")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['message']").value("Reward claimed successfully"));
    }

    @Test
    void testUpdateBalloonProgress() throws Exception {

        updateBalloonProgressRequest = new UpdateBalloonProgressRequest();
        updateBalloonProgressRequest.setPartnershipId(35L);
        updateBalloonProgressRequest.setUserId(5L);
        updateBalloonProgressRequest.setHeliumUsed(250);

        updateBalloonProgressResponse = new UpdateBalloonProgressResponse();
        updateBalloonProgressResponse.setNewBalloonProgress(500);
        updateBalloonProgressResponse.setRemainingHelium(300);

        when(partnershipService.updateBalloonProgress(any(UpdateBalloonProgressRequest.class))).thenReturn(updateBalloonProgressResponse);

        String requestBody = objectMapper.writeValueAsString(updateBalloonProgressRequest);


        mockMvc.perform(put("/partnership/updateBalloonProgress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['newBalloonProgress']").value(500))
                .andExpect(jsonPath("$['remainingHelium']").value(300));

    }


    @Test
    public void testGetBalloonsInfo() throws Exception {

        getBalloonsInfoRequest = new GetBalloonsInfoRequest();
        getBalloonsInfoRequest.setPartnershipId(65L);

        getBalloonsInfoResponse = new GetBalloonsInfoResponse();
        getBalloonsInfoResponse.setUser1Id(35L);
        getBalloonsInfoResponse.setUser2Id(55L);
        getBalloonsInfoResponse.setEventId(33L);
        getBalloonsInfoResponse.setBalloonProgress(770);
        getBalloonsInfoResponse.setRewardClaimed(false);
        getBalloonsInfoResponse.setHeliumCount(400);
        getBalloonsInfoResponse.setActive(true);
        getBalloonsInfoResponse.setAbGroup('B');

        when(partnershipService.getBalloonsInfo(any(GetBalloonsInfoRequest.class))).thenReturn(getBalloonsInfoResponse);

        String requestBody = objectMapper.writeValueAsString(getBalloonsInfoRequest);

        mockMvc.perform(get("/partnership/balloons-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['user1Id']").value(35))
                .andExpect(jsonPath("$['user2Id']").value(55))
                .andExpect(jsonPath("$['eventId']").value(33))
                .andExpect(jsonPath("$['balloonProgress']").value(770))
                .andExpect(jsonPath("$['rewardClaimed']").value(false))
                .andExpect(jsonPath("$['heliumCount']").value(400))
                .andExpect(jsonPath("$['active']").value(true))
                .andExpect(jsonPath("$['abGroup']").value("B"));


    }
}


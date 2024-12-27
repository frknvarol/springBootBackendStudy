package com.dreamgames.backendengineeringcasestudy;


import com.dreamgames.backendengineeringcasestudy.dto.dto.LeaderboardDTO;
import com.dreamgames.backendengineeringcasestudy.service.LeaderboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LeaderboardControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private LeaderboardService leaderboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetTop100Users() throws Exception {
        LeaderboardDTO user1 = new LeaderboardDTO(1L, 67, "frkn");
        LeaderboardDTO user2 = new LeaderboardDTO(2L, 542, "ahmet");
        List<LeaderboardDTO> topUsers = Arrays.asList(user1, user2);

        when(leaderboardService.getTop100Users()).thenReturn(topUsers);

        mockMvc.perform(get("/leaderboard/top100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].username").value("frkn"))
                .andExpect(jsonPath("$[0].level").value(67))
                .andExpect(jsonPath("$[1].userId").value(2L))
                .andExpect(jsonPath("$[1].username").value("ahmet"))
                .andExpect(jsonPath("$[1].level").value(542));
    }
}

package com.dreamgames.backendengineeringcasestudy;

import com.dreamgames.backendengineeringcasestudy.dto.request.CreateUserRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetSuggestionsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateUserProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.CreateUserResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetSuggestionsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateUserProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import com.dreamgames.backendengineeringcasestudy.service.UserService;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Mock
    private CreateUserRequest createUserRequest;

    @Mock
    private CreateUserResponse createUserResponse;

    @Mock
    private UpdateUserProgressRequest updateUserProgressRequest;

    @Mock
    private UpdateUserProgressResponse updateUserProgressResponse;

    @Mock
    private GetSuggestionsRequest getSuggestionsRequest;

    @Mock
    private GetSuggestionsResponse getSuggestionsResponse;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() throws Exception {
        createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test_user");

        createUserResponse = new CreateUserResponse();
        createUserResponse.setId(55L);
        createUserResponse.setLevel(1);
        createUserResponse.setCoins(2000);
        createUserResponse.setAbGroup('A');

        String requestBody = objectMapper.writeValueAsString(createUserRequest);

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(createUserResponse);


        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['id']").value(55));


    }

    @Test
    void testUpdateUserProgress() throws Exception {

        updateUserProgressRequest = new UpdateUserProgressRequest();
        updateUserProgressResponse = new UpdateUserProgressResponse();

        updateUserProgressRequest.setUserId(65L);

        updateUserProgressResponse.setUserId(65L);
        updateUserProgressResponse.setLevel(23);
        updateUserProgressResponse.setCoins(600);


        String requestBody = objectMapper.writeValueAsString(updateUserProgressRequest);

        when(userService.updateUserProgress(any(UpdateUserProgressRequest.class))).thenReturn(updateUserProgressResponse);


        mockMvc.perform(put("/user/{userId}/progress", 65L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['userId']").value(65));
    }


    @Test
    void testGetSuggestions() throws  Exception {

        getSuggestionsRequest = new GetSuggestionsRequest();
        getSuggestionsRequest.setAbGroup('A');

        User user1 = new User("frkn", 'A');
        user1.setId(3L);
        User user2 = new User("vrl", 'A');
        user2.setId(45L);
        User user3 = new User("ahmet", 'A');
        user3.setId(1999L);

        getSuggestionsResponse = new GetSuggestionsResponse();
        getSuggestionsResponse.setSuggestions(Arrays.asList(user1, user2, user3));

        String requestBody = objectMapper.writeValueAsString(getSuggestionsRequest);
        String responseBody = objectMapper.writeValueAsString(getSuggestionsResponse);

        System.out.println(requestBody);

        System.out.println(responseBody);

        when(userService.getSuggestions(any(GetSuggestionsRequest.class))).thenReturn(getSuggestionsResponse);


        mockMvc.perform(get("/user/suggestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.suggestions[0].id").value(3))
                .andExpect(jsonPath("$.suggestions[0].username").value("frkn"))
                .andExpect(jsonPath("$.suggestions[0].coins").value(2000))
                .andExpect(jsonPath("$.suggestions[0].level").value(1))
                .andExpect(jsonPath("$.suggestions[0].abGroup").value("A"))
                .andExpect(jsonPath("$.suggestions[1].id").value(45))
                .andExpect(jsonPath("$.suggestions[1].username").value("vrl"))
                .andExpect(jsonPath("$.suggestions[1].coins").value(2000))
                .andExpect(jsonPath("$.suggestions[1].level").value(1))
                .andExpect(jsonPath("$.suggestions[1].abGroup").value("A"))
                .andExpect(jsonPath("$.suggestions[2].id").value(1999))
                .andExpect(jsonPath("$.suggestions[2].username").value("ahmet"))
                .andExpect(jsonPath("$.suggestions[2].coins").value(2000))
                .andExpect(jsonPath("$.suggestions[2].level").value(1))
                .andExpect(jsonPath("$.suggestions[2].abGroup").value("A"));
    }


}

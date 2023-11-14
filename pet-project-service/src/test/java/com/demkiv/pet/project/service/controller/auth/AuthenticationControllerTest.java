package com.demkiv.pet.project.service.controller.auth;

import com.demkiv.pet.project.service.controller.auth.AuthenticationController;
import com.demkiv.pet.project.service.controller.auth.model.AuthenticationRequest;
import com.demkiv.pet.project.service.controller.auth.model.AuthenticationResponse;
import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;
import com.demkiv.pet.project.service.controller.auth.model.RoleModel;
import com.demkiv.pet.project.service.service.security.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService service;
    @InjectMocks
    private AuthenticationController controller;
    private MockMvc mvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        this.mapper = new ObjectMapper();
    }

    @Test
    @SneakyThrows
    public void whenPingStatus_shouldReturnPong() {
        // given

        String uri = "/api/v1/auth/ping";

        // when
        ResultActions resultActions = mvc.perform(get(uri));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().string("pong"));
    }

    @Test
    public void whenCheckThatRegistrationApi_shouldBePermittedAndReturn200() throws Exception {
        // given
        RoleModel role = RoleModel.builder().name("TEST_ROLE").build();
        RegisterRequest registerRequest = RegisterRequest.builder()
                .role(role)
                .name("testUserName")
                .password("testUserPassword")
                .build();
        AuthenticationResponse response = AuthenticationResponse.builder().jwtToken("testToken").build();
        final String uri = "/api/v1/auth/register";
        given(service.register(registerRequest)).willReturn(response);

        // when
        ResultActions actions = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerRequest)));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    public void whenCheckThatAuthenticationApi_shouldBePermittedAndReturn200() throws Exception {
        // given
        AuthenticationRequest testAuthRequest = AuthenticationRequest.builder()
                .name("testUserName")
                .password("testUserPassword")
                .build();
        AuthenticationResponse response = AuthenticationResponse.builder().jwtToken("testToken").build();
        final String uri = "/api/v1/auth/authenticate";
        given(service.authenticate(testAuthRequest)).willReturn(response);

        // when
        ResultActions actions = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testAuthRequest)));

        // then
        actions.andExpect(status().isOk());
    }
}

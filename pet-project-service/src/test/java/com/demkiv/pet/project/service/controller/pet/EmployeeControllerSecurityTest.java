package com.demkiv.pet.project.service.controller.pet;

import com.demkiv.pet.project.service.controller.auth.AuthenticationController;
import com.demkiv.pet.project.service.controller.auth.model.RegisterRequest;
import com.demkiv.pet.project.service.controller.auth.model.RoleModel;
import com.demkiv.pet.project.service.security.SecurityConfigBeans;
import com.demkiv.pet.project.service.security.SecurityConfiguration;
import com.demkiv.pet.project.service.service.pet.EmployeeService;
import com.demkiv.pet.project.service.service.security.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class EmployeeControllerSecurityTest {
//    @Mock
//    private SecurityConfiguration securityConfiguration;
    @Mock
    private AuthenticationService service;
    @InjectMocks
    private AuthenticationController controller;
    private MockMvc mvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        this.mapper = new ObjectMapper();
    }

    @Test
    @SneakyThrows
    public void whenPingStatus_shouldReturnPong() {
        // given

        String uri = "/api/v1/auth/ping";

        // when
        // then
        mvc.perform(get(uri)).andExpect(status().isOk());
    }

    @Test
//    @WithMockUser(username = "testUser")
    public void whenCheckThatRegistrationApi_shouldBePermittedAndReturn200() throws Exception {
        // given
        RoleModel role = RoleModel.builder().name("TEST_ROLE").build();
        RegisterRequest registerRequest = RegisterRequest.builder()
                .role(role)
                .name("testUserName")
                .password("testUserPassword")
                .build();
        String uri = "/api/v1/auth/register";
        System.out.println(mapper.writeValueAsString(registerRequest));

        mvc.perform(post(uri).param("request", mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser(username = "testUser", authorities = {"READ"})
//    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
//        mvc.perform(get("/api/all/employee").contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk());
//    }
}

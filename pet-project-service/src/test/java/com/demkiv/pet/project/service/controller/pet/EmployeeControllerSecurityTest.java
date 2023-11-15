package com.demkiv.pet.project.service.controller.pet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeControllerSecurityTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = { "WRITE" })
    @Test
    public void whenCheckWRITEAuthority_shouldBeAbleToAddEmployee() throws Exception {
        // given
        final String uri = "/api/add/employee";
        String testEmployee = getTestEmployee();

        // when
        ResultActions actions = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(testEmployee));

        // then
        actions.andExpect(status().isOk());
    }

    @WithMockUser(authorities = { "READ", "DELETE" })
    @Test
    public void whenCheckReadOrDELETEAuthority_shouldNotBeAuthorizedToAddEmployee() throws Exception {
        // given
        final String uri = "/api/add/employee";
        String testEmployee = getTestEmployee();

        // when
        ResultActions actions = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(testEmployee));

        // then
        actions.andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = { "READ" })
    @Test
    public void whenCheckReadAuthority_shouldBeAbleToGetAllEmployees() throws Exception {
        // given
        final String uri = "/api/all/employees";

        // when
        ResultActions actions = mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    public void whenCheckMissedReadAuthority_shouldNotBeAbleToGetAllEmployees() throws Exception {
        // given
        final String uri = "/api/all/employees";

        // when
        ResultActions actions = mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = { "WRITE" })
    @Test
    public void whenCheckWriteAuthority_shouldBeAbleUpdateEmployee() throws Exception {
        // given
        final String uri = "/api/update/employee";
        String testEmployee = """
                {
                  "id": "testEmpId",
                  "name": "testEmpName",
                  "firstName": "testEmpFirstName",
                  "birthDate": "1987-04-06",
                  "position": "testPosition",
                  "salary": 100,
                  "experience": 1,
                  "email": "test.email@emp.com"
                }""";

        // when
        ResultActions actions = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(testEmployee));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    public void whenCheckMissedWriteAuthority_shouldNotBeAbleUpdateEmployee() throws Exception {
        // given
        final String uri = "/api/update/employee";
        String testEmployee = """
                {
                  "id": "testEmpId",
                  "name": "testEmpName",
                  "firstName": "testEmpFirstName",
                  "birthDate": "1987-04-06",
                  "position": "testPosition",
                  "salary": 100,
                  "experience": 1,
                  "email": "test.email@emp.com"
                }""";

        // when
        ResultActions actions = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(testEmployee));

        // then
        actions.andExpect(status().isForbidden());
    }

//    @WithMockUser(authorities = { "DELETE" })
//    @Test
    public void whenCheckDeleteAuthority_shouldBeAbleDeleteEmployee() throws Exception {
        // given
        final String uri = "/api/delete/employee";
        String testId = "testId";

        // when
        ResultActions actions = mockMvc.perform(post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("id", testId));

        // then
        actions.andExpect(status().isOk());
    }

    private String getTestEmployee() {
        return """
                {
                  "name": "testEmpName",
                  "firstName": "testEmpFirstName",
                  "birthDate": "1987-04-06",
                  "position": "testPosition",
                  "salary": 100,
                  "experience": 1,
                  "email": "test.email@emp.com"
                }""";
    }
}

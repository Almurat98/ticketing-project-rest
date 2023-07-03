package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;


    static UserDTO userDTO;
    static ProjectDTO projectDTO;
    static String token;

    @BeforeAll
    static void setUp(){
        token =  "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJvdUpHYWR3R2oyY21rdVU2bGFvZjJBdkpINEprTkl2QnV1VDdUNHk2Q3JrIn0.eyJleHAiOjE2ODg0NTUyODYsImlhdCI6MTY4ODQxOTI4NiwianRpIjoiNzFhODgwMjMtY2Y5Ni00YTcyLTg5ZTctYTdmNjE3Y2IxOWZkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJkNTk3NWNiMi0xZTNmLTRhMjktYjYxYS01NGQxN2E3MWE2ZTkiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjliYTlkNjM0LWM4YWItNDc1NS04ODlhLWEwMmE2Yzk2YTA5ZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIkFkbWluIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiOWJhOWQ2MzQtYzhhYi00NzU1LTg4OWEtYTAyYTZjOTZhMDlmIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImdhaGEifQ.GsUe472sepipyXdwqVAWx_djkmufpHf3doMg3G22kIvddoKfkrX4NwJG8JnUwDiWbh_R5J1_Yqns2OEGdw7pE3Kx--cEbig1lWrVdf8AeSSAQHuycvoVnk1DEcHG28PJSKhG2NibJ6PAG9nDT-kCQH8rOIOoa80MHjI0jUOjjKjfcI5RT6JF9WaMQh7-qb9CyGNFFkUXdR1b-rBGN3Tfh2UFTh37cFDv1rlkgE1seSM3kLM-Hq-mGKUkc5sOjdbFNrnjFMIsWKl0HMbvg1wAvNVlR8_LbanMWAL9RFyRy0RBa_u6im8fd77rzyMhDFcaiEK0oy_9XVu0RAKfzp7Agw";
        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("Oreo")
                .lastName("Aji")
                .userName("OreoA2")
                .passWord("abc")
                .confirmPassWord("abc")
                .role(new RoleDTO(2L,"Manager"))
                .gender(Gender.MALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("API22")
                .projectName("API-OZZY")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("API TEST")
                .projectStatus(Status.OPEN)
                .build();
    }

    @Test
    public void givenNoToken_whenGetRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void givenToken_whenGetRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/project")
                .header("Authorization",token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].projectCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].assignedManager.userName").isNotEmpty());

    }

    @Test
    public void givenToken_updateProject() throws Exception {
        projectDTO.setProjectName("API-video");

        mvc.perform(MockMvcRequestBuilders
                .put("/api/v1/project")
                .header("Authorization",token)
                .content(toJasonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Project is successfully updated"));
    }

    private static String toJasonString(final Object obj){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }


}
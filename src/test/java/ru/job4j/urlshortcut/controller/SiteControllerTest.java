package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;
import ru.job4j.urlshortcut.dto.DomainNameDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc

@AutoConfigureTestDatabase
class SiteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenSiteRegistrationIsSuccess() throws Exception {
        var site = new DomainNameDto("job4j.ru");
        var objMapper = new ObjectMapper().writeValueAsString(site);

        this.mockMvc.perform(post("/url/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("registration").value(true));
    }

    @Test
    @WithMockUser
    public void whenSiteAlreadyRegistration() throws Exception {
        var site = new DomainNameDto("job4j.ru");
        var objMapper = new ObjectMapper().writeValueAsString(site);
        var anotherSite = new DomainNameDto("job4j.ru");
        var anotherObjMapper = new ObjectMapper().writeValueAsString(anotherSite);

        this.mockMvc.perform(post("/url/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/url/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(anotherObjMapper))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("registration").value(false));
    }
}
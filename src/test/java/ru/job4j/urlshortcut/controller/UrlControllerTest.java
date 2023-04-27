package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;
import ru.job4j.urlshortcut.dto.CodeDto;
import ru.job4j.urlshortcut.dto.StatisticDto;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.service.UrlService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @Test
    @WithMockUser
    public void whenUrlConvertShortcut() throws Exception {
        var url = new UrlDto("https://job4j.ru/profile/exercise/106/task-view/532");
        var code = new CodeDto("pHpXWkS");
        var objMapper = new ObjectMapper().writeValueAsString(url);

        when(urlService.save(url)).thenReturn(code);

        this.mockMvc.perform(post("/url/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMapper))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value("pHpXWkS"));
    }

    @Test
    @WithMockUser
    public void whenByCodeRedirectToUrl() throws Exception {
        var code = "pHpXWkS";
        var url = new Url();
        url.setUrl("https://job4j.ru/profile/exercise/106/task-view/532");

        when(urlService.increaseTotalIfPresentCode(code)).thenReturn(Optional.of(url));

        this.mockMvc.perform(get("/url/redirect/{code}", code))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url.getUrl()));
    }

    @Test
    @WithMockUser
    public void whenGetStatisticAllUrls() throws Exception {
        var url1 = new StatisticDto("https://job4j.ru/profile/exercise/106/task-view/532", 5);
        var url2 = new StatisticDto("https://habr.com/ru/companies/jugru/articles/446562/", 2);

        when(urlService.findAll()).thenReturn(List.of(url1, url2));

        this.mockMvc.perform(get("/url/statistic"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].url").value("https://job4j.ru/profile/exercise/106/task-view/532"))
                .andExpect(jsonPath("[0].total").value(5))
                .andExpect(jsonPath("[1].url").value("https://habr.com/ru/companies/jugru/articles/446562/"))
                .andExpect(jsonPath("[1].total").value(2));
    }
}
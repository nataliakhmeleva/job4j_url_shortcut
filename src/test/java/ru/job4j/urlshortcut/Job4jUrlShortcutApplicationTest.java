package ru.job4j.urlshortcut;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureTestDatabase
class Job4jUrlShortcutApplicationTest {

    @Test
    void contextLoads() {
    }
}
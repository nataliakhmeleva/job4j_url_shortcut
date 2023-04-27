package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.DomainNameDto;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.service.SiteService;

import javax.validation.Valid;

@RestController
@RequestMapping("/url")
@AllArgsConstructor
public class SiteController {
    private final SiteService siteService;

    @PostMapping("/registration")
    public ResponseEntity<SiteDto> signUp(@Valid @RequestBody DomainNameDto name) {
        var savedSite = siteService.save(name);
        if (savedSite.getLogin() == null || savedSite.getPassword() == null) {
            throw new NullPointerException("Login and password mustn't be empty");
        }
        if (savedSite.getPassword().length() < 6) {
            throw new IllegalArgumentException("Invalid password");
        }
        return new ResponseEntity<>(savedSite, HttpStatus.CREATED);
    }
}

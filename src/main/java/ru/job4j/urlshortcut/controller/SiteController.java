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
        return new ResponseEntity<>(
                this.siteService.save(name),
                HttpStatus.CREATED
        );
    }
}

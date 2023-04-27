package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.CodeDto;
import ru.job4j.urlshortcut.dto.StatisticDto;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.service.UrlService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/url")
@AllArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/convert")
    public ResponseEntity<CodeDto> convert(@Valid @RequestBody UrlDto urlDto) {
        return new ResponseEntity<>(
                this.urlService.save(urlDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        var url = urlService.increaseTotalIfPresentCode(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "UrlShortCut is not found. Please, check the spelling."
                ));
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url.getUrl()))
                .build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticDto>> statistic() {
        return new ResponseEntity<>(
                this.urlService.findAll(),
                HttpStatus.OK
        );
    }
}

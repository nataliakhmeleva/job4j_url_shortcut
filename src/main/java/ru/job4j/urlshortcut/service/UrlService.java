package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.CodeDto;
import ru.job4j.urlshortcut.dto.StatisticDto;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.job4j.urlshortcut.util.RandomCodeUtil.generateRandomCode;

@Service
@AllArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    Optional<Url> findByUrl(String url) {
        return urlRepository.findByUrl(url);
    }

    public CodeDto save(UrlDto urlDto) {
        var code = generateRandomCode(7);
        var fullUrl = urlDto.getUrl();
        var urlOptional = findByUrl(fullUrl);
        if (urlOptional.isPresent()) {
            return new CodeDto(urlOptional.get().getCode());
        }
        var url = new Url();
        url.setUrl(fullUrl);
        url.setCode(code);
        urlRepository.save(url);

        return new CodeDto(code);
    }

    public Optional<Url> findByCode(String code) {
        return urlRepository.findByCode(code);
    }

    public void increaseTotal(String code) {
        urlRepository.increaseTotal(code);
    }

    public List<StatisticDto> findAll() {
        List<StatisticDto> list = new ArrayList<>();
        for (Url url : urlRepository.findAll()) {
            list.add(new StatisticDto(url.getUrl(), url.getTotal()));
        }
        return list;
    }
}

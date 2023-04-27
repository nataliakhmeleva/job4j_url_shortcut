package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.CodeDto;
import ru.job4j.urlshortcut.dto.StatisticDto;
import ru.job4j.urlshortcut.dto.UrlDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static ru.job4j.urlshortcut.constant.LengthRandomCode.LENGTH_URL_CODE;
import static ru.job4j.urlshortcut.util.RandomCodeUtil.generateRandomCode;

@Service
@AllArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    public Optional<Url> findByUrl(String url) {
        return urlRepository.findByUrl(url);
    }

    public Optional<Url> findByCode(String code) {
        return urlRepository.findByCode(code);
    }

    public void increaseTotal(String code) {
        urlRepository.increaseTotal(code);
    }

    @Transactional
    public Optional<Url> increaseTotalIfPresentCode(String code) {
        var url = findByCode(code);
        if (url.isPresent()) {
            increaseTotal(code);
        }
        return url;
    }

    public List<StatisticDto> findAll() {
        return urlRepository.findAllUrl();
    }

    @Transactional
    public CodeDto save(UrlDto urlDto) {
        var code = createCode();
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

    private String createCode() {
        var code = generateRandomCode(LENGTH_URL_CODE.getLength());
        while (findByCode(code).isPresent()) {
            code = generateRandomCode(LENGTH_URL_CODE.getLength());
        }
        return code;
    }
}

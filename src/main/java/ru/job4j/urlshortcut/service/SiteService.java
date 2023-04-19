package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.DomainNameDto;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;

import static ru.job4j.urlshortcut.util.RandomCodeUtil.generateRandomCode;

@Service
@AllArgsConstructor
public class SiteService {
    private final SiteRepository siteRepository;
    private BCryptPasswordEncoder encoder;

    public Optional<Site> findByName(String name) {
        return siteRepository.findByName(name);
    }

    Site findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    public SiteDto save(DomainNameDto name) {
        var login = generateRandomCode(7);
        var password = generateRandomCode(6);
        var domainName = name.getDomainName();
        var siteOptional = findByName(domainName);
        if (siteOptional.isPresent()) {
            return new SiteDto(false, siteOptional.get().getLogin(), siteOptional.get().getPassword());
        }

        var site = new Site();
        site.setName(domainName);
        site.setLogin(login);
        site.setPassword(encoder.encode(password));
        siteRepository.save(site);

        return new SiteDto(true, login, password);
    }
}

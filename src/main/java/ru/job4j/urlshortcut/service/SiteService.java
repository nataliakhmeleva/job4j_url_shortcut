package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.DomainNameDto;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.mapper.SiteMapper;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static ru.job4j.urlshortcut.constant.LengthRandomCode.LENGTH_SITE_LOGIN;
import static ru.job4j.urlshortcut.constant.LengthRandomCode.LENGTH_SITE_PASSWORD;
import static ru.job4j.urlshortcut.util.RandomCodeUtil.generateRandomCode;

@Service
@AllArgsConstructor
public class SiteService implements UserDetailsService {
    private final SiteRepository siteRepository;
    private BCryptPasswordEncoder encoder;
    private SiteMapper siteMapper;

    public Optional<Site> findByName(String name) {
        return siteRepository.findByName(name);
    }

    public Optional<Site> findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }

    @Transactional
    public SiteDto save(DomainNameDto name) {
        var login = createLogin();
        var password = generateRandomCode(LENGTH_SITE_PASSWORD.getLength());
        var domainName = name.getDomainName();
        var siteOptional = findByName(domainName);
        if (siteOptional.isPresent()) {
            return siteMapper.toDto(siteOptional.get());
        }

        var site = new Site();
        site.setName(domainName);
        site.setLogin(login);
        site.setPassword(encoder.encode(password));
        siteRepository.save(site);

        return new SiteDto(true, login, password);
    }

    private String createLogin() {
        var login = generateRandomCode(LENGTH_SITE_LOGIN.getLength());
        while (findByLogin(login).isPresent()) {
            login = generateRandomCode(LENGTH_SITE_LOGIN.getLength());
        }
        return login;
    }
}

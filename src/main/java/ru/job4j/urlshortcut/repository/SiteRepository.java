package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

public interface SiteRepository extends CrudRepository<Site, Integer> {
    Optional<Site> findByName(String name);
    Site findByLogin(String login);
}

package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Url;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    Optional<Url> findByUrl(String url);

    Optional<Url> findByCode(String code);

    @Modifying
    @Transactional
    @Query("UPDATE Url u SET u.total = u.total + 1 WHERE u.code = ?1")
    void increaseTotal(String code);
}

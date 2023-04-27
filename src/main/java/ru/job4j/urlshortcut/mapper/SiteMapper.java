package ru.job4j.urlshortcut.mapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.job4j.urlshortcut.dto.DomainNameDto;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;

@Mapper(componentModel = "spring", imports = {RandomStringUtils.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMapper {
    @Mapping(target = "registration", expression = "java(false)")
    SiteDto toDto(Site site);

    /**
     * Метод toModel демонстрирует возможности Mapstruct. Не используется в приложении.
     */
    @Mapping(target = "login", expression = "java(RandomStringUtils.randomAlphanumeric(7))")
    @Mapping(target = "password", expression = "java(RandomStringUtils.randomAlphanumeric(7))")
    @Mapping(source = "domainNameDto.domainName", target = "name")
    Site toModel(DomainNameDto domainNameDto);
}

package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {
    @NotBlank(message = "URL of site must be not empty")
    private String url;
    @NotNull(message = "Total must be non null")
    private int total;
}

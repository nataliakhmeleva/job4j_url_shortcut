package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {
    @NotBlank(message = "Code must be not empty")
    @Size(min = 6, message = "Code is too short")
    private String code;
}

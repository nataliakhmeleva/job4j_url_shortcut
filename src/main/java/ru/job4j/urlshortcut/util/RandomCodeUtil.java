package ru.job4j.urlshortcut.util;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@NoArgsConstructor
public class RandomCodeUtil {
    public static String generateRandomCode(int len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }
}

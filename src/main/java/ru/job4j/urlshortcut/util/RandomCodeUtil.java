package ru.job4j.urlshortcut.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomCodeUtil {
    public static String generateRandomCode(int len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }
}

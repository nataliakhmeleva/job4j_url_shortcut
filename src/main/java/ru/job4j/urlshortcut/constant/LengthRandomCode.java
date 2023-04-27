package ru.job4j.urlshortcut.constant;

public enum LengthRandomCode {
    LENGTH_URL_CODE(7),
    LENGTH_SITE_LOGIN(8),
    LENGTH_SITE_PASSWORD(6);

    private final int length;

    LengthRandomCode(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}

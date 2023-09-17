package org.heroes.consumer;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class CryptoText {

    public String generateMd5(String text) {
        return text == null ? "" : DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
    }
}

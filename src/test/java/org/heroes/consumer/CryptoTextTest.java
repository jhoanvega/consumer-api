package org.heroes.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoTextTest {

    private final CryptoText cryptoText = new CryptoText();

    @Test
    public void convertirApiKeyCorrectamente() {
        String md5 = cryptoText.generateMd5("16994f8f242379488835d71c560398d6375d9293f625f40945130218cafc9423a83e88a84");
        assertEquals("af6b7017e260cd9e88e8f231713c0bfa", md5);
    }

    @Test
    public void convertirApiKeyValorVacio() {
        String md5 = cryptoText.generateMd5("");
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", md5);
    }

    @Test
    public void convertirApiKeyValorNulo() {
        String md5 = cryptoText.generateMd5(null);
        assertEquals("", md5);
    }

    @Test
    public void convertirApiKeyCaracteresEspeciales() {
        String md5 = cryptoText.generateMd5("*/854?¡¡+++");
        assertEquals("c9d9feb1495bdf81e3d7f52a98f14d79", md5);
    }
}
package org.heroes.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ContextConfiguration(classes = {ContextSpring.class})
class ConsumerAPITest {

    @Autowired
    private ConsumerAPI consumerAPI;


    @Test
    public void consumirApiComicsCorrectamente() {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("ts", "1");
        uriVariables.put("apikey", "625f40945130218cafc9423a83e88a84");
        uriVariables.put("hash", "af6b7017e260cd9e88e8f231713c0bfa");
        String url = "http://gateway.marvel.com/v1/public/characters?ts={ts}&apikey={apikey}&hash={hash}";
        consumerAPI.consumeGet(url, uriVariables, ResponseMarvel.class)
                .ifPresent(resp -> {
                    assertEquals(1563, resp.getData().getTotal());
                    assertEquals("3-D Man", resp.getData().getResults().get(0).getName());
                    assertEquals("", resp.getData().getResults().get(0).getDescription());
                    assertEquals(1011334, resp.getData().getResults().get(0).getId());
                });

    }

    @Test
    public void consumirApiCredencialesInvalidas() {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("ts", "1");
        uriVariables.put("apikey", "1625f40945130218cafc9423a83e88a84");
        uriVariables.put("hash", "af6b7017e260cd9e88e8f231713c0bfa");
        String url = "http://gateway.marvel.com/v1/public/characters?ts={ts}&apikey={apikey}&hash={hash}";
        HttpClientErrorException excepcion = assertThrows(HttpClientErrorException.class,
                () -> consumerAPI.consumeGet(url, uriVariables, ResponseMarvel.class));
        assertEquals(excepcion.getRawStatusCode(), 401);
        assertEquals(excepcion.getResponseBodyAsString(), "{\"code\":\"InvalidCredentials\",\"message\":\"The passed API key is invalid.\"}");
    }

    @Test
    public void consumirApiUrlIncorrecta() {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("ts", "1");
        uriVariables.put("apikey", "1625f40945130218cafc9423a83e88a84");
        uriVariables.put("hash", "af6b7017e260cd9e88e8f231713c0bfa");
        String url = "http://gateway.marvel.com/v1/public/nofound?ts={ts}&apikey={apikey}&hash={hash}";
        HttpClientErrorException excepcion = assertThrows(HttpClientErrorException.class,
                () -> consumerAPI.consumeGet(url, uriVariables, ResponseMarvel.class));
        assertEquals(excepcion.getRawStatusCode(), 404);
        assertEquals(excepcion.getResponseBodyAsString(), "{\"code\":\"ResourceNotFound\",\"message\":\"/v1/public/nofound?ts=1&apikey=1625f40945130218cafc9423a83e88a84&hash=af6b7017e260cd9e88e8f231713c0bfa does not exist\"}");
    }

    @Test
    public void consumirApiServicioCaido() {
        Map<String, String> uriVariables = new HashMap<>();
        String url = "http://localhost:1000";
        ResourceAccessException excepcion = assertThrows(ResourceAccessException.class,
                () -> consumerAPI.consumeGet(url, uriVariables, ResponseMarvel.class));
        assertEquals(excepcion.getMessage(), "I/O error on GET request for \"http://localhost:1000\": Connection refused (Connection refused); nested exception is java.net.ConnectException: Connection refused (Connection refused)");
    }

}
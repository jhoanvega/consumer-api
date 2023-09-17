package org.heroes.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                });

    }
}
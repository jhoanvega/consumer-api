package org.heroes.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ConsumerAPI {
    private RestTemplate restTemplate;

    @Autowired
    public ConsumerAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> Optional<T> consumeGet(String url, Map<String, String> uriVariables, Class<T> responseType) {
        ResponseEntity<T> response = restTemplate.getForEntity(url, responseType, uriVariables);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            return Optional.empty();
        }
    }
}

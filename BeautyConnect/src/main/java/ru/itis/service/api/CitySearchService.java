package ru.itis.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;



@Service
@RequiredArgsConstructor
public class CitySearchService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> searchCities(String query) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", query)
                .queryParam("format", "json")
                .queryParam("addressdetails", "1")
                .queryParam("limit", "10")
                .queryParam("accept-language", "ru") // Для русских названий, если есть
                .build()
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        List<String> cities = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            for (JsonNode node : root) {
                String displayName = node.get("display_name").asText();
                String type = node.has("type") ? node.get("type").asText() : "";
                if ("city".equals(type) || "town".equals(type)) {
                    cities.add(displayName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к Nominatim", e);
        }

        return cities;
    }
}



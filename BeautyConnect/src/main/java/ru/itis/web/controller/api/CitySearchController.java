package ru.itis.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.service.api.CitySearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CitySearchController {

    private final CitySearchService citySearchService;

    @GetMapping("/api/cities/search")
    public List<String> searchCities(@RequestParam("q") String query) {
        return citySearchService.searchCities(query);
    }
}


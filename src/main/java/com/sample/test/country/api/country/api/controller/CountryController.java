package com.sample.test.country.api.country.api.controller;


import com.sample.test.country.api.country.api.exception.CustomException;
import com.sample.test.country.api.country.api.model.dto.response.CountryResDto;
import com.sample.test.country.api.country.api.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "${base-url.context}/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryResDto>> getCountries(@RequestParam(value = "name") String name) throws CustomException {
        log.info("Received request to get countries details");
        return new ResponseEntity<>(countryService.getCountries(name), HttpStatus.OK);
    }
}

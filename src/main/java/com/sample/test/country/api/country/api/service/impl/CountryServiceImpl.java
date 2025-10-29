package com.sample.test.country.api.country.api.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.test.country.api.country.api.exception.CustomException;
import com.sample.test.country.api.country.api.model.dto.external.CountryExDto;
import com.sample.test.country.api.country.api.model.dto.response.CountryResDto;
import com.sample.test.country.api.country.api.model.entity.Country;
import com.sample.test.country.api.country.api.model.repository.CountryRepository;
import com.sample.test.country.api.country.api.service.CountryService;
import com.sample.test.country.api.country.api.service.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sample.test.country.api.country.api.exception.pojo.ExceptionCode.SBT001;

@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final HttpService<String> httpService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @Value(value = "${country.api}")
    private String countryAPI;

    public CountryServiceImpl(CountryRepository countryRepository, HttpService<String> httpService, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.httpService = httpService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CountryResDto> getCountries(String name) throws CustomException {
        log.debug("getCountries method started...");
        String url = countryAPI + name;
        String response = httpService.get(url, null, null);

        try {
            List<CountryExDto> countryExternal =
                    objectMapper.readValue(response, new TypeReference<List<CountryExDto>>() {});

            List<Country> countryList = countryExternal
                    .stream()
                    .map(countryExDto -> modelMapper.map(countryExDto, Country.class))
                    .toList();

            countryList = countryRepository.saveAll(countryList);
            return countryList.stream().map(country -> modelMapper.map(country, CountryResDto.class)).toList();
        }catch (Exception e){
            throw new CustomException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    SBT001,
                    e.getMessage()
            );
        }
    }
}

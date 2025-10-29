package com.sample.test.country.api.country.api.service;

import com.sample.test.country.api.country.api.exception.CustomException;
import com.sample.test.country.api.country.api.model.dto.response.CountryResDto;

import java.util.List;

public interface CountryService {
    List<CountryResDto> getCountries(String name) throws CustomException;
}

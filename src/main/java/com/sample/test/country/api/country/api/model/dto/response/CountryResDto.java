package com.sample.test.country.api.country.api.model.dto.response;

import lombok.Data;

@Data
public class CountryResDto {

    private long id;
    private String commonName;
    private String capital;
    private String region;
    private String subregion;
    private int population;
}

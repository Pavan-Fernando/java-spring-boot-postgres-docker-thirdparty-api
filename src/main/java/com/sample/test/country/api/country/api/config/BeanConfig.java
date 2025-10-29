package com.sample.test.country.api.country.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.test.country.api.country.api.model.dto.external.CountryExDto;
import com.sample.test.country.api.country.api.model.entity.Country;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // ✅ Define converter to safely extract first capital
        Converter<CountryExDto, String> capitalConverter = ctx -> {
            CountryExDto src = ctx.getSource();
            if (src.getCapital() != null && !src.getCapital().isEmpty()) {
                return src.getCapital().get(0);
            }
            return null;
        };

        // ✅ Custom mapping from CountryExDto → Country
        modelMapper.addMappings(new PropertyMap<CountryExDto, Country>() {
            @Override
            protected void configure() {
                // Map name.common → commonName
                map().setCommonName(source.getName().getCommon());

                // Use converter for capital
                using(capitalConverter).map(source).setCapital(null);

                // Direct mappings
                map().setRegion(source.getRegion());
                map().setSubregion(source.getSubregion());
                map().setPopulation((int) source.getPopulation());
            }
        });

        return modelMapper;
    }
}

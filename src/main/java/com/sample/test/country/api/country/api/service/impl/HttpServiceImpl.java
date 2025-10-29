package com.sample.test.country.api.country.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.test.country.api.country.api.exception.CustomException;
import com.sample.test.country.api.country.api.service.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

import static com.sample.test.country.api.country.api.exception.pojo.ExceptionCode.SBT004;

@Slf4j
@Service
public class HttpServiceImpl<T> implements HttpService<T> {

    private final RestTemplate restTemplate;


    public HttpServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String get(String url, String pathId, Map<String, Object> params) throws CustomException {
        return call(getQueryParamUrl(url, pathId, params), null, HttpMethod.GET, new HttpHeaders()).getBody();
    }



    private ResponseEntity<String> call(String url, T body, HttpMethod httpMethod, HttpHeaders userRequestHeaders) throws CustomException {

        try {

            HttpHeaders headers = new HttpHeaders(userRequestHeaders);
            if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            log.info("API response: {}", responseEntity);
            return responseEntity;

        } catch (HttpStatusCodeException ex) {
            log.warn("HttpClientErrorException occurred. Error - {}", ex.getMessage());
            throw new CustomException(
                    ex.getStatusCode() instanceof HttpStatus status ? status : null,
                    SBT004,
                    ex.getMessage()
            );
        }
    }

    private String getQueryParamUrl(String url, String id, Map<String, Object> queryParameters) {

        if (StringUtils.hasText(id)) {
            url = url + '/' + id;
        }

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        if (!CollectionUtils.isEmpty(queryParameters)) {
            queryParameters.forEach(uriComponentsBuilder::queryParam);
        }
        return uriComponentsBuilder.buildAndExpand().toUriString();
    }

}

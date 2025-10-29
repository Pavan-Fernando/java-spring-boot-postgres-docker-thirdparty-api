package com.sample.test.country.api.country.api.service;

import com.sample.test.country.api.country.api.exception.CustomException;

import java.util.Map;

public interface HttpService<T> {

    String get(String url, String pathId, Map<String, Object> params) throws CustomException;
}

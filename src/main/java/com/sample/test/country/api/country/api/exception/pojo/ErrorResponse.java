package com.sample.test.country.api.country.api.exception.pojo;

import com.sample.test.country.api.country.api.model.dto.response.ErrorRespDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private ErrorRespDto error;

    public ErrorResponse(String code, String type, String message) {
        ErrorRespDto errorResponse = new ErrorRespDto();
        errorResponse.setCode(code);
        errorResponse.setType(type);
        errorResponse.setMessage(message);

        this.error = errorResponse;
    }
}

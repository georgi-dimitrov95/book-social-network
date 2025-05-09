package com.bsn.api.exception;

import lombok.*;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private String error;

    private Set<String> validationErrors;

    private Map<String, String> errorsMap;
}

package com.cristiancmello.awslambdascheduledeventsample;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class LambdaRequest {
    private String message;
}

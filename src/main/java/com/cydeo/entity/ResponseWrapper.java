package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@NoArgsConstructor
public class ResponseWrapper {

    private boolean success;
    private String message;
    private Integer code;
    private Object data;

    public ResponseWrapper( String message, Object data,HttpStatus httpStatus) {
        this.success = true;
        this.message = message;
        this.code =httpStatus.value();
        this.data = data;
    }

    public ResponseWrapper(String message) {
        this.success = true;
        this.message = message;
        this.code = HttpStatus.OK.value();
    }

    public ResponseWrapper(String message, HttpStatus httpStatus) {
        this.success = true;
        this.message = message;
        this.code = httpStatus.value();
    }
}

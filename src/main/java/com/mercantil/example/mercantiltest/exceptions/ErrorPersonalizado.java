package com.mercantil.example.mercantiltest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorPersonalizado extends RuntimeException{
    private String messagee;
    private HttpStatus status;
    private String messageDto;

}

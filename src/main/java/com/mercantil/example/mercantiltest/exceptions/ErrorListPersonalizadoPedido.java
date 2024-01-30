package com.mercantil.example.mercantiltest.exceptions;

import com.mercantil.example.mercantiltest.exceptions.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorListPersonalizadoPedido extends RuntimeException {
    private String messagee;
    private HttpStatus status;
    private List<ErrorDto> errorDto;

}

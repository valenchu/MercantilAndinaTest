package com.mercantil.example.mercantiltest.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


/**
 * @author Valentín Cassino
 * @implNote ErrorDto es un dto simple que manejara la respuesta de errores, puede mejorarse
 */
@Data
@AllArgsConstructor
@Builder
public class ErrorDto {
    private String message;
    private Integer code;
    private HttpStatus status;
    private String messageDto;
    private List<ErrorDto> errorDto;

    public ErrorDto() {
    }

    public ErrorDto(String message, int code, HttpStatus status, String messageDto) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.messageDto = messageDto;
    }

    public ErrorDto(String message, int code, HttpStatus status, List<ErrorDto> errorDto) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errorDto = errorDto;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", status=" + status +
                ", messageDto='" + messageDto + '\'' +
                // Excluir errorDto si cumple cierta condición
                (debeExcluirErrorDto() ? "" : ", errorDto=" + errorDto) +
                '}';
    }

    private boolean debeExcluirErrorDto() {
        // Implementa la lógica para determinar si errorDto debe ser excluido
        // Por ejemplo, si errorDto es null o una lista vacía
        return errorDto == null || errorDto.isEmpty();
    }

}

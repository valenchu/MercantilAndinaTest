package com.mercantil.example.mercantiltest.exceptions;

import com.mercantil.example.mercantiltest.exceptions.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Valentín Cassino
 * @implNote Clase que se encarga del manejo de exepciones, clase simple exception
 */
@RestControllerAdvice
public class ControllerAdvice {
    private List<ErrorDto> errores = new ArrayList<>();

    @ExceptionHandler(value = ErrorPersonalizado.class)
    public ResponseEntity<?> runtimeExeptionHandler(ErrorPersonalizado errorDto) {
        ErrorDto dto = ErrorDto.builder().message(errorDto.getMessagee()).code(errorDto.getStatus().value()).status(errorDto.getStatus()).messageDto(errorDto.getMessageDto()).build();
        return new ResponseEntity<>(dto, errorDto.getStatus());
    }

    @ExceptionHandler(value = ErrorListPersonalizadoPedido.class)
    public ResponseEntity<?> runtmeErrorPedidoHandler(ErrorListPersonalizadoPedido errorListPersonalizadoPedido){
        return new ResponseEntity<>(errorListPersonalizadoPedido,errorListPersonalizadoPedido.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        errores = ex.getBindingResult().getFieldErrors().stream()
                .map(excep -> ErrorDto.builder()
                        .message("Error: " + excep.getDefaultMessage())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .messageDto(excep.getField())
                        .build())
                .sorted(Comparator.comparing(this::customSort)) // Ordenar utilizando un comparador personalizado
                .collect(Collectors.toList());
        // Crea una respuesta adecuada basada en los errores de validación
        return ResponseEntity.badRequest().body(errores);
    }


    private String customSort(ErrorDto errorDto) {
        // Puedes ajustar la lógica de ordenación según tus necesidades
        return errorDto.getMessage();
    }
}

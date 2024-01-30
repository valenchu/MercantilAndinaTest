package com.mercantil.example.mercantiltest.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UtilsFechaHorario {
    private static volatile UtilsFechaHorario instancia;


    private UtilsFechaHorario() {
    }

    public static UtilsFechaHorario getInstance() {
        if (instancia == null) {
            synchronized (UtilsFechaHorario.class) {
                if (instancia == null) {
                    instancia = new UtilsFechaHorario();
                }
            }
        }
        return instancia;
    }


    public LocalTime convertirStringAHorario(String horario) {
        horario = horario.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(horario, formatter);
    }

    public String convertirHorarioAString(LocalTime horario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horario.format(formatter);
    }
}

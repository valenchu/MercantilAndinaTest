package com.mercantil.example.mercantiltest.configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    @Scheduled(fixedRate = 25 * 60 * 1000) // 25min mantengo heroku on
    public void ejecutarTareaProgramada() {
        System.out.println("Ejecutando tarea programada...");
    }
}

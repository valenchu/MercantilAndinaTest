package com.mercantil.example.mercantiltest.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class ProductoEntity {
    @Id
    private UUID id;

    private String nombre;
    private String descripcionCorta;
    private String descripcionLarga;
    private Double precioUnitario;
}

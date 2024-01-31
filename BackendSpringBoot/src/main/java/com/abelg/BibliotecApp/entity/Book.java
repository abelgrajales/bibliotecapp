package com.abelg.BibliotecApp.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "libro")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String nombre;
    private String editorial;
    private String autor;
    private String categoria;
    private String descripcion;
    private long cantidad;

}

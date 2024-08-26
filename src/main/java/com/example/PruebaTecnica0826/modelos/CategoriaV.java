package com.example.PruebaTecnica0826.modelos;

import jakarta.persistence.*;

public class CategoriaV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreV;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreV() {
        return nombreV;
    }

    public void setNombreV(String nombreV) {
        this.nombreV = nombreV;
    }
}

package com.example.PruebaTecnica0826.modelos;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ProductoV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreV;
    private BigDecimal precioV;
    private Integer existenciaV;
    @ManyToOne
    @JoinColumn(name = "id_categoriav", referencedColumnName = "id")
    private CategoriaV categoriaV;

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

    public BigDecimal getPrecioV() {
        return precioV;
    }

    public void setPrecioV(BigDecimal precioV) {
        this.precioV = precioV;
    }

    public Integer getExistenciaV() {
        return existenciaV;
    }

    public void setExistenciaV(Integer existenciaV) {
        this.existenciaV = existenciaV;
    }

    public CategoriaV getCategoriaV() {
        return categoriaV;
    }

    public void setCategoriaV(CategoriaV categoriaV) {
        this.categoriaV = categoriaV;
    }
}

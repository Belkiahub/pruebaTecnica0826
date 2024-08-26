package com.example.PruebaTecnica0826.modelos;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
public class EtiquetaV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreV;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "productoV_id", referencedColumnName = "id")
    private ProductoV productoV;

    // Constructor vacío
    public EtiquetaV() {}

    // Constructor que acepta Producto y String
    public EtiquetaV(ProductoV productoV, String nombreV) {
        this.productoV = productoV;
        this.nombreV = nombreV;
    }

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

    @Nonnull
    public ProductoV getProductoV() {
        return productoV;
    }

    public void setProductoV(ProductoV productoV) {
        this.productoV = productoV;
    }
}

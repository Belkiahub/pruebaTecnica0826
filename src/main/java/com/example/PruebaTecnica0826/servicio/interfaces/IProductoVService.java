package com.example.PruebaTecnica0826.servicio.interfaces;

import com.example.PruebaTecnica0826.modelos.ProductoV;
import org.springframework.data.domain.*;

import java.util.Optional;

public interface IProductoVService {
    Page<ProductoV> obtenerTodos(Pageable pageable);


    Optional<ProductoV> buscarPorId(Long id);

    ProductoV crearOEditar(ProductoV productoV);

    void eliminarPorId(Long id);
}

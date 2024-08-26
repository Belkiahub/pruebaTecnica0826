package com.example.PruebaTecnica0826.servicio.implementacion;

import com.example.PruebaTecnica0826.modelos.ProductoV;
import com.example.PruebaTecnica0826.repositorio.IProductoVRepository;
import com.example.PruebaTecnica0826.servicio.interfaces.IProductoVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdcutoVService implements IProductoVService {
    @Autowired
    private IProductoVRepository productoRepository;

    @Override
    public Page<ProductoV> obtenerTodos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductoV> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public ProductoV crearOEditar(ProductoV productoV) {
        return productoRepository.save(productoV);
    }

    @Override
    public void eliminarPorId(Long id) {
        productoRepository.deleteById(id);
    }
}

package com.example.PruebaTecnica0826.servicio.implementacion;


import com.example.PruebaTecnica0826.modelos.CategoriaV;
import com.example.PruebaTecnica0826.repositorio.ICategoriaVRepository;
import com.example.PruebaTecnica0826.servicio.interfaces.ICategoriaVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoriaVService implements ICategoriaVService {
    @Autowired
    private ICategoriaVRepository categoriaVRepository;

    @Override
    public List<CategoriaV> obtenerTodos() {
        return categoriaVRepository.findAll();
    }

    @Override
    public Optional<CategoriaV> buscarPorId(Long id) {
        return categoriaVRepository.findById(id);
    }

    @Override
    public CategoriaV crearOEditar(CategoriaV categoriaV) {
        return categoriaVRepository.save(categoriaV);
    }

    @Override
    public void eliminarPorId(Long id) {
        categoriaVRepository.deleteById(id);
    }

    @Override
    public List<CategoriaV> listarTodosLasCategoria() {
        return categoriaVRepository.findAll();
    }
}

package com.example.PruebaTecnica0826.servicio.interfaces;

import com.example.PruebaTecnica0826.modelos.CategoriaV;

import java.util.List;
import java.util.Optional;

public interface ICategoriaVService {

    List<CategoriaV> obtenerTodos();


    Optional<CategoriaV> buscarPorId(Long id);

    CategoriaV crearOEditar(CategoriaV categoriaV);

    void eliminarPorId(Long id);

    List<CategoriaV> listarTodosLasCategoria();


}

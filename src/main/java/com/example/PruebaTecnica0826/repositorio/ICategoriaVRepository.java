package com.example.PruebaTecnica0826.repositorio;

import com.example.PruebaTecnica0826.modelos.CategoriaV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaVRepository extends JpaRepository<CategoriaV, Long> {
}

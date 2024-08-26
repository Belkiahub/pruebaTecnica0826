package com.example.PruebaTecnica0826.repositorio;

import com.example.PruebaTecnica0826.modelos.EtiquetaV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEtiquetaVRepository extends JpaRepository<EtiquetaV, Long> {
}

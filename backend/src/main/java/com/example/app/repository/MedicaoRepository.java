package com.example.app.repository;

import com.example.app.model.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    List<Medicao> findByUsuarioId(String usuarioId);
}

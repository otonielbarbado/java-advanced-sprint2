package com.example.app.service;

import com.example.app.model.Medicao;
import com.example.app.repository.MedicaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class MedicaoService {
    private final MedicaoRepository repo;
    public MedicaoService(MedicaoRepository repo) { this.repo = repo; }

    public Medicao save(Medicao m) { return repo.save(m); }
    public Page<Medicao> list(Pageable p) { return repo.findAll(p); }
    public Optional<Medicao> findById(Long id) { return repo.findById(id); }
    public void deleteById(Long id) { repo.deleteById(id); }
    public List<Medicao> findByUsuario(String usuarioId) { return repo.findByUsuarioId(usuarioId); }
}

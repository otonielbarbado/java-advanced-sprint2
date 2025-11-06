package com.example.app.controller;

import com.example.app.model.Medicao;
import com.example.app.service.MedicaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/medicoes")
public class MedicaoController {
    private final MedicaoService service;
    public MedicaoController(MedicaoService service) { this.service = service; }

    @GetMapping("/{id}")
    public EntityModel<Medicao> getOne(@PathVariable Long id) {
        Medicao m = service.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        EntityModel<Medicao> model = EntityModel.of(m,
            linkTo(methodOn(MedicaoController.class).getOne(id)).withSelfRel(),
            linkTo(methodOn(MedicaoController.class).listAll(Pageable.unpaged())).withRel("medicoes"),
            linkTo(methodOn(MedicaoController.class).delete(id)).withRel("delete")
        );
        return model;
    }

    @GetMapping
    public CollectionModel<EntityModel<Medicao>> listAll(Pageable pageable) {
        Page<Medicao> page = service.list(pageable);
        List<EntityModel<Medicao>> medicoes = page.stream()
            .map(m -> EntityModel.of(m,
              linkTo(methodOn(MedicaoController.class).getOne(m.getId())).withSelfRel()))
            .collect(Collectors.toList());
        return CollectionModel.of(medicoes,
            linkTo(methodOn(MedicaoController.class).listAll(pageable)).withSelfRel()
        );
    }

    @PostMapping
    public ResponseEntity<EntityModel<Medicao>> create(@RequestBody Medicao medicao) {
        Medicao saved = service.save(medicao);
        EntityModel<Medicao> model = EntityModel.of(saved,
            linkTo(methodOn(MedicaoController.class).getOne(saved.getId())).withSelfRel()
        );
        return ResponseEntity.created(linkTo(methodOn(MedicaoController.class).getOne(saved.getId())).toUri()).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Medicao>> update(@PathVariable Long id, @RequestBody Medicao medicao) {
        Medicao existing = service.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existing.setLocal(medicao.getLocal());
        existing.setNivelRuido(medicao.getNivelRuido());
        existing.setDataHora(medicao.getDataHora());
        existing.setUsuarioId(medicao.getUsuarioId());
        Medicao saved = service.save(existing);
        EntityModel<Medicao> model = EntityModel.of(saved,
            linkTo(methodOn(MedicaoController.class).getOne(saved.getId())).withSelfRel()
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public CollectionModel<EntityModel<Medicao>> medicoesPorUsuario(@PathVariable String usuarioId) {
        List<Medicao> list = service.findByUsuario(usuarioId);
        List<EntityModel<Medicao>> medicoes = list.stream()
            .map(m -> EntityModel.of(m, linkTo(methodOn(MedicaoController.class).getOne(m.getId())).withSelfRel()))
            .collect(Collectors.toList());
        return CollectionModel.of(medicoes, linkTo(methodOn(MedicaoController.class).medicoesPorUsuario(usuarioId)).withSelfRel());
    }
}

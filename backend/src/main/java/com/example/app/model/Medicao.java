package com.example.app.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEDICAO")
public class Medicao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_medicao")
    @SequenceGenerator(name = "seq_medicao", sequenceName = "SEQ_MEDICAO", allocationSize = 1)
    private Long id;

    @Column(name = "LOCAL_MEDICAO", nullable = false, length = 200)
    private String local;

    @Column(name = "NIVEL_RUIDO", nullable = false)
    private Double nivelRuido;

    @Column(name = "DATA_HORA")
    private LocalDateTime dataHora;

    @Column(name = "USUARIO_ID")
    private String usuarioId;

    public Medicao() {
        this.dataHora = LocalDateTime.now();
    }

    public Medicao(String local, Double nivelRuido, LocalDateTime dataHora, String usuarioId) {
        this.local = local;
        this.nivelRuido = nivelRuido;
        this.dataHora = dataHora == null ? LocalDateTime.now() : dataHora;
        this.usuarioId = usuarioId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public Double getNivelRuido() { return nivelRuido; }
    public void setNivelRuido(Double nivelRuido) { this.nivelRuido = nivelRuido; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}

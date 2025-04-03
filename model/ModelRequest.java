package com.testelemontech.solicitacoes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "model_requests")
public class ModelRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo adicionado para mapear a coluna 'codigo_solicitacao'
    @Column(name = "codigo_solicitacao", nullable = false, length = 255)
    private String codigoSolicitacao;

    @Column(name = "nome_passageiro", nullable = false, length = 255)
    private String nomePassageiro;

    @Column(name = "cia_aerea", nullable = false, length = 255)
    private String ciaAerea;

    @Column(name = "data_hora_saida", nullable = false)
    private LocalDateTime dataHoraSaida;

    @Column(name = "data_hora_chegada", nullable = false)
    private LocalDateTime dataHoraChegada;

    @Column(name = "cidade_origem", nullable = false, length = 255)
    private String cidadeOrigem;

    @Column(name = "cidade_destino", nullable = false, length = 255)
    private String cidadeDestino;

    @Column(name = "status", nullable = false, length = 255)
    private String status;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    public ModelRequest() {}

    @PrePersist
    public void prePersist() {
        updatedAt = LocalDateTime.now();
        // Se codigoSolicitacao não estiver preenchido, gera um valor padrão
        if (codigoSolicitacao == null || codigoSolicitacao.isEmpty()) {
            codigoSolicitacao = generateCodigoSolicitacao();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String generateCodigoSolicitacao() {
        // Exemplo de geração de código usando o timestamp
        return "SOL-" + System.currentTimeMillis();
    }
}

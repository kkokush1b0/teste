package com.testelemontech.solicitacoes.controller;

import com.testelemontech.solicitacoes.model.ModelRequest;
import com.testelemontech.solicitacoes.service.ModelRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ModelRequestController {

    private final ModelRequestService service;

    @GetMapping
    public ResponseEntity<List<ModelRequest>> getAllSolicitacoes() {
        List<ModelRequest> solicitacoes = service.getAllModelRequests();
        return solicitacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/sincronizar")
    public ResponseEntity<?> sincronizarSolicitacoes() {
        try {
            List<ModelRequest> novasSolicitacoes = service.sincronizarSolicitacoes();
            if (novasSolicitacoes.isEmpty()) {
                return ResponseEntity.ok("Nenhuma nova solicitação encontrada.");
            }
            return ResponseEntity.ok(novasSolicitacoes);
        } catch (Exception e) {
            log.error("Erro ao sincronizar solicitações: ", e);
            return ResponseEntity.internalServerError().body("Erro ao sincronizar: " + e.getMessage());
        }
    }
}

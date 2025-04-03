package com.testelemontech.solicitacoes.service;

import com.testelemontech.solicitacoes.model.ModelRequest;
import com.testelemontech.solicitacoes.repository.ModelRequestRepository;
import com.testelemontech.solicitacoes.config.WsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelRequestService {

    private final ModelRequestRepository modelRequestRepository;
    private final WsClient wsClient;

    public List<ModelRequest> getAllModelRequests() {
        return modelRequestRepository.findAll();
    }

    @Transactional
    public List<ModelRequest> sincronizarSolicitacoes() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(3);
        LocalDateTime endDate = LocalDateTime.now();

        List<ModelRequest> modelRequests = wsClient.BuscarSolicitacoes(startDate, endDate);

        if (modelRequests == null || modelRequests.isEmpty()) {
            log.info(" Nenhuma nova solicitação encontrada.");
            return List.of();
        }

        log.info(" Sincronizando {} solicitações...", modelRequests.size());

        // Garante que os dados sejam tratados corretamente antes de salvar
        modelRequests.forEach(request -> {
            request.setId(null); 
            request.setUpdatedAt(LocalDateTime.now());
        });

        return modelRequestRepository.saveAll(modelRequests);
    }
}

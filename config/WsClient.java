package com.testelemontech.solicitacoes.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.testelemontech.solicitacoes.model.ModelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lemontech.selfbooking.wsselfbooking.beans.Solicitacao;
import br.com.lemontech.selfbooking.wsselfbooking.beans.aereo.Aereo;
import br.com.lemontech.selfbooking.wsselfbooking.beans.aereo.AereoSeguimento;
import br.com.lemontech.selfbooking.wsselfbooking.services.WsSelfBooking;
import br.com.lemontech.selfbooking.wsselfbooking.services.WsSelfBookingService;
import br.com.lemontech.selfbooking.wsselfbooking.services.request.PesquisarSolicitacaoRequest;
import br.com.lemontech.selfbooking.wsselfbooking.services.response.PesquisarSolicitacaoResponse;
import jakarta.xml.bind.JAXBElement;

@Service
public class WsClient {

    @Value("${soap.keyClient}")
    private String clientKey;

    @Value("${soap.username}")
    private String username;

    @Value("${soap.password}")
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(WsClient.class);

    @Autowired
    private WsSelfBookingService wsSelfBookingService;

    public List<ModelRequest> BuscarSolicitacoes(LocalDateTime startDate, LocalDateTime endDate) {
        logger.info("Solicitações de {} a {}", startDate, endDate);

        PesquisarSolicitacaoRequest request = buildRequest(startDate, endDate);
        logger.debug("Solicitação: {}", request);

        try {
            logger.info("Chamando serviço");
            WsSelfBooking port = wsSelfBookingService.getWsSelfBookingPort();
            PesquisarSolicitacaoResponse response = port.pesquisarSolicitacao(
                    clientKey,
                    username,
                    password,
                    request
            );

            logger.info("Resultados de Busca: {}", response);
            return processResponse(response);
        } catch (Exception e) {
            logger.error("Erro de busca: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private PesquisarSolicitacaoRequest buildRequest(LocalDateTime startDate, LocalDateTime endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        PesquisarSolicitacaoRequest request = new PesquisarSolicitacaoRequest();

        logger.debug("Data de Inicio: {}", startDate.format(formatter));
        request.getContent().add(new JAXBElement<>(new QName("dataInicial"), String.class, startDate.format(formatter)));

        logger.debug("Data Final: {}", endDate.format(formatter));
        request.getContent().add(new JAXBElement<>(new QName("dataFinal"), String.class, endDate.format(formatter)));

        request.getContent().add(new JAXBElement<>(new QName("registroInicial"), Integer.class, 1));

        return request;
    }

    private List<ModelRequest> processResponse(PesquisarSolicitacaoResponse response) {
        List<ModelRequest> modelRequests = new ArrayList<>();

        if (response == null || response.getSolicitacao() == null) {
            logger.warn("Vazio ou nulo");
            return modelRequests;
        }

        for (Solicitacao solicitacao : response.getSolicitacao()) {
            if (solicitacao.getAereos() != null) {
                for (Aereo aereo : solicitacao.getAereos().getAereo()) {
                    if (aereo.getAereoSeguimento() != null) {
                        for (AereoSeguimento segment : aereo.getAereoSeguimento()) {
                            logger.debug("Solicitação processada: {}", segment);
                            ModelRequest request = mapToModelRequest(solicitacao, aereo, segment);
                            modelRequests.add(request);
                        }
                    }
                }
            }
        }

        logger.info("Numero de solicitações processadas: {}", modelRequests.size());
        return modelRequests;
    }

    private ModelRequest mapToModelRequest(Solicitacao solicitacao, Aereo aereo, AereoSeguimento segment) {
        ModelRequest request = new ModelRequest();

        logger.debug("Busca solicitações por ID: {}", solicitacao.getIdSolicitacao());
        request.setId(Long.valueOf(solicitacao.getIdSolicitacao()));
        request.setNomePassageiro(solicitacao.getPassageiros().getPassageiro().get(0).getNomeCompleto());
        request.setCiaAerea(aereo.getSource());
        request.setDataHoraSaida(ConversorData.convertXMLGregorianCalendarToLocalDateTime(segment.getDataSaida()));
        request.setDataHoraChegada(ConversorData.convertXMLGregorianCalendarToLocalDateTime(segment.getDataChegada()));
        request.setCidadeOrigem(segment.getCidadeOrigem());
        request.setCidadeDestino(segment.getCidadeDestino());

        return request;
    }
}
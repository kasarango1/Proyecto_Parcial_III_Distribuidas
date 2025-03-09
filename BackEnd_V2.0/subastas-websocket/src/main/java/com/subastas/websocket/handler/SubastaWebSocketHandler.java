package com.subastas.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subastas.websocket.models.PujaMessage;
import com.subastas.websocket.models.SubastaUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubastaWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Map<Long, Map<String, WebSocketSession>> subastaSubscribers = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long subastaId = extractSubastaId(session);
        if (subastaId != null) {
            subastaSubscribers.computeIfAbsent(subastaId, k -> new ConcurrentHashMap<>())
                    .put(session.getId(), session);
            
            notifySubscribers(subastaId, new SubastaUpdate(
                "USUARIO_CONECTADO",
                subastaId,
                null,
                null,
                session.getAttributes().get("userEmail").toString(),
                LocalDateTime.now(),
                "Usuario conectado a la subasta"
            ));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            PujaMessage pujaMessage = objectMapper.readValue(message.getPayload(), PujaMessage.class);
            Long subastaId = extractSubastaId(session);
            
            if (subastaId != null && validatePuja(pujaMessage, session)) {
                SubastaUpdate update = new SubastaUpdate(
                    "NUEVA_PUJA",
                    subastaId,
                    pujaMessage.getSubastaAutoId(),
                    pujaMessage.getMonto(),
                    pujaMessage.getCompradorNombre(),
                    LocalDateTime.now(),
                    "Nueva puja registrada"
                );
                
                notifySubscribers(subastaId, update);
            }
        } catch (Exception e) {
            log.error("Error procesando mensaje: {}", e.getMessage());
            session.sendMessage(new TextMessage("{\"error\": \"Error procesando la puja\"}"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long subastaId = extractSubastaId(session);
        if (subastaId != null) {
            Map<String, WebSocketSession> subscribers = subastaSubscribers.get(subastaId);
            if (subscribers != null) {
                subscribers.remove(session.getId());
                if (subscribers.isEmpty()) {
                    subastaSubscribers.remove(subastaId);
                }
            }
        }
    }

    public void notifySubastaStatus(Long subastaId, String tipo, String mensaje) {
        SubastaUpdate update = new SubastaUpdate(
            tipo,
            subastaId,
            null,
            null,
            null,
            LocalDateTime.now(),
            mensaje
        );
        notifySubscribers(subastaId, update);
    }

    private void notifySubscribers(Long subastaId, SubastaUpdate update) {
        Map<String, WebSocketSession> subscribers = subastaSubscribers.get(subastaId);
        if (subscribers != null) {
            String message;
            try {
                message = objectMapper.writeValueAsString(update);
                TextMessage textMessage = new TextMessage(message);
                
                subscribers.values().forEach(session -> {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(textMessage);
                        }
                    } catch (IOException e) {
                        log.error("Error enviando mensaje a sesión: {}", e.getMessage());
                    }
                });
            } catch (Exception e) {
                log.error("Error serializando mensaje: {}", e.getMessage());
            }
        }
    }

    private Long extractSubastaId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] segments = path.split("/");
        try {
            return Long.parseLong(segments[segments.length - 1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private boolean validatePuja(PujaMessage pujaMessage, WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");
        return userId != null && userId.equals(pujaMessage.getCompradorId());
    }
} 
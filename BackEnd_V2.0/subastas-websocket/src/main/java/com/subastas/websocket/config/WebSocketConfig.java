package com.subastas.websocket.config;

import com.subastas.websocket.handler.SubastaWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SubastaWebSocketHandler subastaWebSocketHandler;
    private final JwtInterceptor jwtInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(subastaWebSocketHandler, "/ws/subastas")
                .addInterceptors(jwtInterceptor)
                .setAllowedOrigins("http://localhost:3000"); // Frontend React
    }
} 
package com.subastas.websocket.config;

import com.subastas.websocket.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request,
                                 org.springframework.http.server.ServerHttpResponse response,
                                 WebSocketHandler wsHandler,
                                 Map<String, Object> attributes) throws Exception {
        String token = extractToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            attributes.put("userId", jwtTokenProvider.getUserIdFromToken(token));
            attributes.put("userEmail", jwtTokenProvider.getEmailFromToken(token));
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(org.springframework.http.server.ServerHttpRequest request,
                             org.springframework.http.server.ServerHttpResponse response,
                             WebSocketHandler wsHandler,
                             Exception exception) {
    }

    private String extractToken(org.springframework.http.server.ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 
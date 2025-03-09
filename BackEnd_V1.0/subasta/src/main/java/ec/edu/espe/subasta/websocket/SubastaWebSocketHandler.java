package ec.edu.espe.subasta.websocket;

import ec.edu.espe.subasta.entity.Puja;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Este controlador maneja los mensajes enviados a través del WebSocket para las pujas.
 * Los clientes se conectarán al endpoint configurado (por ejemplo, "/ws-subasta") y enviarán
 * mensajes a "/app/pujas". Las pujas serán retransmitidas a todos los suscriptores en "/topic/subasta".
 */
@Controller
public class SubastaWebSocketHandler {

    @MessageMapping("/pujas")
    @SendTo("/topic/subasta")
    public Puja enviarPuja(Puja puja) {
        // Aquí se podría agregar lógica adicional, por ejemplo, validación o actualización en BD.
        // Por ahora, simplemente se reenvía la puja recibida a todos los clientes suscritos.
        return puja;
    }
}

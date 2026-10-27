package prueba;

import io.quarkus.websockets.next.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import static java.util.Objects.requireNonNull;

@WebSocket(path = "/arduino")
public class Intermediario {

    @Inject
    WebSocketConnection connection;

    @OnOpen
    public void onOpen() {

    }

    @OnClose
    public void onClose() {

    }

    @OnTextMessage
    public void  onMessage(String message) {
        System.out.println("onMessage> " + message);

        broadcast(message);
    }

    private void broadcast(String message) {
        for (WebSocketConnection openConnection : connection.getOpenConnections()) {
            // Envía el mensaje de forma segura y síncrona en el Virtual Thread
            openConnection.sendTextAndAwait(message);
        }
    }
}

package de.uni_osnabrueck.ikw.eegdroid;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.drafts.Draft;
//import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class WSClient extends WebSocketClient {

//    public WSClient(URI serverUri, Draft draft) {
//        super(serverUri, draft);
//    }

    public WSClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("WS","new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("WS","closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        Log.d("WS", "received message: " + message);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        Log.d("WS", "received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        Log.d("WS", "an error occurred:" + ex);
    }

//    public static void main(String[] args) throws URISyntaxException {
//        WebSocketClient client = new WSClient(new URI("ws://localhost:8887"));
//        client.connect();
//    }
}
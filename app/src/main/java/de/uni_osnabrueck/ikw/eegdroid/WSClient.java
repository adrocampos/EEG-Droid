package de.uni_osnabrueck.ikw.eegdroid;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class WSClient extends WebSocketClient {


    public WSClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("WS", "new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("WS", "closed with exit code " + code + " additional info: " + reason);
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

}
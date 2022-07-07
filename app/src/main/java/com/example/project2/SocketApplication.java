package com.example.project2;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;

public class SocketApplication {
    static Socket socket;
    public static Socket get() throws URISyntaxException {
        socket = IO.socket("172.10.5.152:443");
        return socket;
    }
}

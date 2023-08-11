package com.yinrj.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author yinrongjie
 * @date 2023/8/10
 * @name SocketClient
 */
@Slf4j
public class SocketClient {
    private final String serverHost;

    private final int serverPort;

    private Socket socket;


    public SocketClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            this.socket = new Socket(serverHost, serverPort);
        } catch (IOException e) {
            log.error("IO exception.", e);
        }
    }

    public void request() {
        for (;;) {
            try {
                if (this.socket == null) {
                    connect();
                }
                this.socket.getOutputStream().write((new Date() + ":Hello server.").getBytes());
                this.socket.getOutputStream().flush();
                Thread.sleep(2000);
            } catch (Exception e) {
                log.error("There is an exception when request.", e);
            }
        }
    }

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 3333);
        client.request();
    }
}

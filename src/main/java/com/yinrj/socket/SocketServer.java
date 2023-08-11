package com.yinrj.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yinrongjie
 * @date 2023/8/10
 * @name SocketServer
 */
@Slf4j
public class SocketServer {
    private static final int DEFAULT_PORT = 3333;

    private final int port;

    public SocketServer() {
        this(DEFAULT_PORT);
    }

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() {
        log.info("Server start.....");
        // 创建ServerSocket对象
        try(ServerSocket serverSocket = new ServerSocket(this.port)) {
            for (;;) {
                // 调用阻塞方法，直到获取新的连接
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        byte[] data = new byte[1024];
                        InputStream inputStream = socket.getInputStream();
                        while (true) {
                            int len;
                            while ((len = inputStream.read(data)) != -1) {
                                log.info(new String(data, 0, len));
                            }
                        }
                    } catch (IOException e) {
                        log.info("IO exception.", e);
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.start();
    }
}

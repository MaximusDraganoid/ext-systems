package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225);

        while (true) {
            Socket client = socket.accept(); //повисаем и ждем каких либо изменений или новостей
            //и получаем сокет клиента, который подключился к нашему серверу.
            new SimpleServer(client).start();
        }

    }

    private static void handleRequest(Socket client) throws IOException, InterruptedException {
    }
}

class SimpleServer extends Thread{

    private Socket client;

    public SimpleServer(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            Thread.sleep(2000);
            StringBuilder sb = new StringBuilder("Hello, ");
            String userName = br.readLine();
            sb.append(userName);
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();

            br.close();
            bw.close();
            client.close();//закрываем после обработки сетевого соединения

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}

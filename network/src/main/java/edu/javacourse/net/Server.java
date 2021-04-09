package edu.javacourse.net;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225);

        Map<String, Greetable> handlers = loadHandlers();


        while (true) {
            Socket client = socket.accept(); //повисаем и ждем каких либо изменений или новостей
            //и получаем сокет клиента, который подключился к нашему серверу.
            new SimpleServer(client, handlers).start();
        }

    }

    private static Map<String, Greetable> loadHandlers() {
        Map<String, Greetable> result = new HashMap<>();

        try(InputStream is = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            for (Object command : properties.keySet()) {
                String className = properties.getProperty(command.toString());
                Class<Greetable> cl = (Class<Greetable>)Class.forName(className);
                Greetable handler = cl.getConstructor().newInstance();
                result.put(command.toString(), handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static void handleRequest(Socket client) throws IOException, InterruptedException {
    }
}

class SimpleServer extends Thread{

    private Socket client;
    private Map<String, Greetable> handlers;

    public SimpleServer(Socket client, Map<String, Greetable> handlers) {
        this.client = client;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            StringBuilder sb = new StringBuilder("");
            String request = br.readLine();
            String[] lines = request.split("\\s+");
            String command = lines[0];
            String userName = lines[1];

            System.out.println("Server got " + command + " and " + userName);

            String response = buildResponse(command, userName);

            sb.append(response );
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

    private String buildResponse(String command, String userName) {
        Greetable handler = handlers.get(command);
        if (handler != null) {
            return handler.buildResponse(userName);
        }

        return "Hello, " + userName;
    }
}

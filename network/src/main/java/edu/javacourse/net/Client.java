package edu.javacourse.net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 12; i++) {
            SimpleClient simpleClient = new SimpleClient();
            simpleClient.start();
        }
    }

}

class SimpleClient extends Thread {

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 25225);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String sb = "Maxim";

            bw.write(sb);
            bw.newLine();
            bw.flush();

            String answer = br.readLine();
            System.out.println(answer);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}

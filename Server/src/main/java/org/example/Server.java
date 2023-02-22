package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ClientService> clients;

    public Server(int port) {
        this.clients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.print(String.format("Cервер успешно стартовал на порту %d\n", port));
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Подключен клиент " + socket.getInetAddress());
                ClientService clientService = new ClientService(socket, clients.size() + 1, "nick", this);
                clients.add(clientService);
                clientService.start();

            }
            } catch(IOException e){
                throw new RuntimeException(e);
            }


    }


    public void deleteClient(ClientService clientService) {

        for (ClientService client : clients) {
            if (client == clientService) {
                clients.remove(clientService);
            }
        }
    }



    public void messageForAll(String message) {

        for (ClientService client : clients) {
                client.sendMessage(message);


        }
    }
}



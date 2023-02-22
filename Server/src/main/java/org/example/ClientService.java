package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientService extends Thread {
    private int id;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    Server server;

    String nick;

    public ClientService(Socket socket, int id,String nick, Server server) throws IOException {
        this.id = id;
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        this.server = server;
        this.nick=nick;

    }

    @Override
    public void run() {
        super.run();

        try {
            while (true) {
                String message = in.readUTF();
                System.out.println(message);
                server.messageForAll(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("Соединение с клиентом " + socket.getInetAddress() + " потеряно");
        } finally {
            closeStreams();
        }
    }


    public void closeStreams() {
        server.deleteClient(this);
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getClientId() {
        return id;
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

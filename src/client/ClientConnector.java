package client;

import com.scrom.model.action.ScromAction;

import java.io.*;
import java.net.Socket;

/**
 * Created by jeppe on 15-03-15.
 */
public class ClientConnector {
    final String SERVER_ADDRESS = "77.233.229.51";
    final int SERVER_PORT = 60500;
    Client client;
    Boolean listening;
    Socket channel;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    private BufferedReader in;
    private PrintWriter out;
    public ClientConnector(Client c){
        client = c;
        connect();
        receive();
        //disconnect();
    }

    private void disconnect() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(){
        try {
            channel = new Socket(SERVER_ADDRESS, SERVER_PORT);
            ois = new ObjectInputStream(channel.getInputStream());
            oos = new ObjectOutputStream(channel.getOutputStream());

        } catch (Exception e){
            System.out.println("OH NO!");
        }
    }
    private void receive(){
        listening = true;
        while(listening){
            ScromAction action = null;
            try {
                System.out.println(in.readLine());
                action = (ScromAction)ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            client.perform(action);
        }
    }
    public void send(ScromAction action){
        try {
            oos.writeObject(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.scrom.client;

import com.scrom.model.action.ScromAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public ClientConnector(Client c, String name){
        client = c;
        connect(name);
        receive();
        disconnect();
    }

    private void disconnect() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(String name){
        try {
            channel = new Socket(SERVER_ADDRESS, SERVER_PORT);
            ois = new ObjectInputStream(channel.getInputStream());
            oos = new ObjectOutputStream(channel.getOutputStream());
            oos.writeBytes(name);
        } catch (Exception e){
            System.out.println("OH NO!");
        }
    }
    private void receive(){
        listening = true;
        while(listening){
            ScromAction action = null;
            try {
                action = (ScromAction)ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            client.perform(action);
        }
    }
    public void stop(){
        listening = false;
    }
    public void send(ScromAction action){
        try {
            oos.writeObject(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

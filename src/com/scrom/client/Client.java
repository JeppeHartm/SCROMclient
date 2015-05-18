package com.scrom.client;

import com.scrom.model.Player;
import com.scrom.model.SCROM;
import com.scrom.model.action.PlayerAction;
import com.scrom.model.action.ScromAction;
import com.scrom.model.action.ServerAction;

/**
 * Created by jeppe on 12-03-15.
 */
public class Client {
    public final String ID;
    ClientConnector con;
    SCROM game;
    public Client(String name){
        ID = name;
        con = new ClientConnector(this,name);
    }
    public boolean hasTurn(){
        return ((game.getCurrent()).getID() == ID);
    }
    public SCROM.GameState getState(){
        return game.getState();
    }
    //Perform is called on an instance of ScromAction to change the game state in accordance to it.
    public void perform(ScromAction action){
        assert action != null;
        if(action instanceof PlayerAction){
            PlayerAction pa = (PlayerAction)action;
            switch(pa.getActionType()){
                case CardPlayed:
                    Player player = game.getPlayer(pa.getPlayerId());
                    player.playCard(pa.getCard());
            }
        }else if(action instanceof ServerAction){
            ServerAction sa = (ServerAction)action;
            switch(sa.getActionType()){

            }
        }
    }
}

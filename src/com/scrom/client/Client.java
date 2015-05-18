package com.scrom.client;

import com.scrom.model.Player;
import com.scrom.model.SCROM;
import com.scrom.model.action.PlayerAction;
import com.scrom.model.action.ScromAction;
import com.scrom.model.action.ServerAction;
import com.scrom.model.card.Card;
import com.scrom.model.card.event.EventCard;

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
                    player.playCard((Card)pa.getSubject());
                    break;
            }
        }else if(action instanceof ServerAction){
            ServerAction sa = (ServerAction)action;
            switch(sa.getActionType()){
                case Initialize:
                    game = (SCROM)sa.getSubject();
                    break;
                case NewTurn:
                    game.incrementTurn();
                    game.setGameState(SCROM.GameState.waiting);
                    break;
                case CardDealt:
                    String id = sa.getPlayerId();
                    EventCard ec = (EventCard) sa.getSubject();
                    game.setCurrent(game.getPlayer(id));
                    game.setCurrentCard(ec);
                    break;
            }
        }
    }
}

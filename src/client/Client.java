package client;

import com.scrom.model.Player;
import com.scrom.model.SCROM;
import com.scrom.model.action.PlayerAction;
import com.scrom.model.action.ScromAction;
import com.scrom.model.action.ServerAction;
import com.scrom.model.card.Card;

/**
 * Created by jeppe on 12-03-15.
 */
public class Client {
    ClientConnector con;
    SCROM game;

    public Client(){
        con = new ClientConnector(this);
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
            }
        }
    }

}

package client;

import com.scrom.model.Player;
import com.scrom.model.SCROM;
import com.scrom.model.action.ScromAction;

/**
 * Created by jeppe on 12-03-15.
 */
public class Client {
    Player player;
    SCROM game;
    public void perform(ScromAction action){
        assert action != null;
    }
}

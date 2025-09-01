package tttc.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import tttc.exception.GenericGameException;
import tttc.model.Player;


@Component
@SessionScope
public class SessionPlayer extends SessionWrapper <Player> {

    public SessionPlayer() {
        System.out.println("SessionPlayer created" + this);
    }

    @Override
    public Player getObject() {
        Player player = super.getObject();
        if (player == null) {
            throw new GenericGameException("Player not found in session");
        }
        return player;
    }
}

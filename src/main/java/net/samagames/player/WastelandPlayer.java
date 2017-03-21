package net.samagames.player;

import net.samagames.api.games.GamePlayer;
import org.bukkit.entity.Player;

/**
 * Created by werter on 21.03.2017.
 */
public class WastelandPlayer extends GamePlayer {
    private Player player;
    private Team team;

    public WastelandPlayer(Player player){
        super(player);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean hasTeam(){
        return team != null;
    }

    public boolean isInTeam(TeamColor color){
        boolean isInTeam = false;
        if(hasTeam())
            if(team.getTeamColor().equals(color))
                isInTeam = true;
        return isInTeam;
    }
}

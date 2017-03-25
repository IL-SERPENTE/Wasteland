package net.samagames.player;

import net.samagames.api.games.GamePlayer;
import net.samagames.tools.chat.ActionBarAPI;
import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.entity.Player;

/**
 * Created by werter on 21.03.2017.
 */
public class WastelandPlayer extends GamePlayer {

    private Player player;
    private ObjectiveSign scoreBoard;
    private Team team;
    private int wheat;

    public WastelandPlayer(Player player){
        super(player);
        this.player = player;
    }

    public ObjectiveSign getScoreBoard(){ return this.scoreBoard;}

    public void setScoreBoard(ObjectiveSign scoreBoard){
        this.scoreBoard = scoreBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public int getWheat(){
        return this.wheat;
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

    public void updateScoreBoard(){
        getScoreBoard().setLine(1, "Vous avez: " + getWheat() + " blés sur vous");
        getScoreBoard().updateLines();
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
        updateScoreBoard();
    }

    public void addWheat(int number){
        this.wheat = wheat + number;
        updateScoreBoard();
    }

}

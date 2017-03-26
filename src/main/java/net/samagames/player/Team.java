package net.samagames.player;

import net.samagames.Wasteland;
import net.samagames.entity.Turret;
import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 21.03.2017.
 */
public class Team {

    private int wheat;
    private Team ennemies;
    private Location chestLocation,spawn;
    private Wasteland wasteland;
    private TeamColor teamColor;
    private List<Player> member;
    private Turret[] turrets;

    public Team(Wasteland wasteland,TeamColor color,Location spawn ,Location chestLocation){
        this.wasteland = wasteland;
        this.teamColor = color;
        this.spawn = spawn;
        this.chestLocation = chestLocation;
        this.member = new ArrayList<>();

    }

    public void updateScoreBoard(){
        for(Player player : this.member){
            WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
            ObjectiveSign objectiveSign =wastelandPlayer.getScoreBoard();
            objectiveSign.setLine(3, "Votre coffre contient: " + wastelandPlayer.getTeam().getWheat());
            objectiveSign.updateLines();
        }
    }

    public void initScoreBoard(){
        for(Player player : this.member) {
            ObjectiveSign scoreBoard = new ObjectiveSign(this.getTeamColor().name(),this.getTeamColor().getChatColor() + "Team " + this.getTeamColor().getName());
            WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
            wastelandPlayer.setScoreBoard(scoreBoard);
            ObjectiveSign objectiveSign =wastelandPlayer.getScoreBoard();
            objectiveSign.setLine(0, " ");
            objectiveSign.setLine(1, "Vous avez: " + wastelandPlayer.getWheat() + " blés sur vous");
            objectiveSign.setLine(2, "   ");
            objectiveSign.setLine(3, "Votre coffre contient: " + wastelandPlayer.getTeam().getWheat());
            objectiveSign.setLine(4, "      ");
            objectiveSign.setLine(5, "00:00");
            objectiveSign.addReceiver(player);
        }
    }

    public void setEnnemies(Team ennemies) {
        this.ennemies = ennemies;
    }

    public Team getEnnemies() {
        return ennemies;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    public int getWheat(){return  this.wheat;}

    public void setWheat(int wheat) {
        this.wheat = wheat;
        updateScoreBoard();
    }

    public void removeWheat(int wheat){
        this.wheat = this.wheat - wheat;
        updateScoreBoard();
    }

    public void addWheat(int wheat){
        this.wheat = +wheat + this.wheat;
        updateScoreBoard();
    }

    public void setTurrets (Turret... turrets){
        this.turrets = turrets;
    }

    public Turret[] getTurrets() {
        return turrets;
    }

    public TeamColor getTeamColor(){
        return this.teamColor;
    }

    public void addPlayer(Player player){
        this.member.add(player);
        player.setPlayerListName(this.getTeamColor().getChatColor() + player.getName());
        wasteland.getWastelandPlayer(player).setTeam(this);
    }

    public void removePlayer(Player player){
        this.member.remove(player);
    }

    public boolean contains(Player player){
        return member.contains(player);
    }

    public List<Player> getMember() {
        return member;
    }
}

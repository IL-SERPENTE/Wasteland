package net.samagames.player;

import net.samagames.Wasteland;
import net.samagames.entity.Turret;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 21.03.2017.
 */
public class Team {

    private int wheat;
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

    public Location getSpawn() {
        return spawn;
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    public int getWheat(){return  this.wheat;}

    public void setWheat(int wheat) { this.wheat = wheat;}

    public void addWheat(int wheat){this.wheat = +wheat + this.wheat;}

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

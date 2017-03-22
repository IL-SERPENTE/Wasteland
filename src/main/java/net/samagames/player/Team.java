package net.samagames.player;

import net.samagames.Wasteland;
import net.samagames.entity.Turret;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 21.03.2017.
 */
public class Team {
    private Wasteland wasteland;
    private TeamColor teamColor;
    private List<Player> member;
    private Turret[] turrets;

    public Team(Wasteland wasteland,TeamColor color){
        this.wasteland = wasteland;
        this.teamColor = color;
        this.member = new ArrayList<>();
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

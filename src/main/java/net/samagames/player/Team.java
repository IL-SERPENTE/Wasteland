package net.samagames.player;

import net.samagames.entity.Turret;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 21.03.2017.
 */
public class Team {
    private TeamColor teamColor;
    private List<Player> member;
    private Turret[] turrets;

    public Team(TeamColor color){
        this.teamColor = color;
        this.member = new ArrayList<>();
    }


    public Turret[] getTurrets() {
        return turrets;
    }

    public TeamColor getTeamColor(){
        return this.teamColor;
    }

    public void addPlayer(Player player){
        this.member.add(player);
        new WastelandPlayer(player).setTeam(this);
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

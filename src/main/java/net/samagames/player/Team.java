package net.samagames.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 21.03.2017.
 */
public class Team {
    TeamColor teamColor;
    List<Player> member;
    public Team(TeamColor color){
        this.teamColor = color;
        this.member = new ArrayList<>();
    }


    public TeamColor getTeamColor(){
        return this.teamColor;
    }

    public void addPlayer(Player player){
        this.member.add(player);
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

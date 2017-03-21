package net.samagames.entity;

import net.samagames.player.Team;
import org.bukkit.Location;

/**
 * Created by werter on 21.03.2017.
 */
public class Turret {
    private Team team;
    private Location location;
    private int range;

    public Turret (Team team, Location location, int range){
        this.team = team;
        this.location = location;
        this.range = range;
    }



    public void enable(){
        //TODO
    }

    public void init(){
        //TODO
    }
}

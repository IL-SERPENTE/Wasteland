package net.samagames.entity;

import net.samagames.Wasteland;
import net.samagames.player.Team;
import net.samagames.player.WastelandPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by werter on 21.03.2017.
 */
public class Turret {

    private Wasteland wasteland;
    private Team team;
    private Location location;
    private int range;

    public Turret(Wasteland wasteland, Team team, Location location, int range) {
        this.wasteland = wasteland;
        this.team = team;
        this.location = location;
        this.range = range;
    }


    private BukkitRunnable bukkitRunnable;


    public void enable(){
        bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
                    if (wastelandPlayer.hasTeam())
                        if (!wastelandPlayer.getTeam().equals(team) && location.distance(player.getLocation()) <= range) {
                            ShulkerBullet shulkerBullet = player.getWorld().spawn(location,ShulkerBullet.class);
                            shulkerBullet.setTarget(player);
                        }
                }
            }
        };        bukkitRunnable.runTaskTimer(wasteland.getMain(),20,100);

    }

    public void disable(){
        bukkitRunnable.cancel();
    }

    public void init(){
        location.getBlock().setType(Material.AIR);

    }
}

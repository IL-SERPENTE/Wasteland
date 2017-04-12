package net.samagames.entity;

import net.samagames.Wasteland;
import net.samagames.player.Team;
import net.samagames.player.WastelandPlayer;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by werter on 21.03.2017.
 */
public class Turret implements Listener{

    private Wasteland wasteland;
    private Location[] locations;
    private Team team;
    private Location location;
    private int range;

    public Turret(){}

    public Turret(Wasteland wasteland, Team team, Location location, int range, Location... locations) {
        this.wasteland = wasteland;
        this.team = team;
        this.locations = locations;
        this.location = location;
        this.range = range;
    }


    private BukkitRunnable bukkitRunnable;


    public void enable(){
        new BukkitRunnable(){
            @Override
            public void run() {
                System.out.println("2 minutes");
                for(Location location : locations) new Plant(location);
            }
        }.runTaskTimer(wasteland.getMain(),20,20*120);
        bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
                    if (wastelandPlayer.hasTeam())
                        if (!wastelandPlayer.getTeam().equals(team) && location.distance(player.getLocation()) <= range) {
                            player.damage(4);
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

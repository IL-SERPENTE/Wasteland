package net.samagames.entity;

import net.samagames.Wasteland;
import net.samagames.player.Team;
import net.samagames.player.WastelandPlayer;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

/**
 * Created by werter on 21.03.2017.
 */
public class Turret implements Listener{

    private BukkitRunnable bukkitRunnable;
    private Wasteland wasteland;
    private Location[] locations;
    private Team team;
    private Location location;
    private int range;
    private HashMap<Player,Integer> playerInTurret = new HashMap<>();

    public Turret(){}

    public Turret(Wasteland wasteland, Team team, Location location, int range, Location... locations) {
        this.wasteland = wasteland;
        this.team = team;
        this.locations = locations;
        this.location = location;
        this.range = range;
    }



    public void enable(){
        bukkitRunnable = new BukkitRunnable() {
            int seconds = 0;
            @Override
            public void run() {
                seconds = seconds + 5;
                if(seconds == 120) {
                    if(locations != null)
                        for (Location location : locations) new Plant(location).spawn();

                    seconds = 0;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
                    if (wastelandPlayer.hasTeam())
                        if (!wastelandPlayer.getTeam().equals(team) && location.distance(player.getLocation()) <= range) {
                            player.damage(4);
                            shoot(player.getLocation());
                        }
                }
            }
        };        bukkitRunnable.runTaskTimer(wasteland.getMain(),20,100);
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(getEnnemiesInTurret() < getTeamInTurret()){
                        playerInTurret.forEach((k,v)->playerInTurret.remove(k));
                        break;
                    }
                    WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
                    if (!wastelandPlayer.getTeam().equals(team) && location.distance(player.getLocation()) <= range) {
                        if(playerInTurret.get(player) != null && playerInTurret.get(player) == 10) {
                            team = wastelandPlayer.getTeam();
                            player.playSound(player.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1,1);
                            player.sendMessage("Vous avez capturé l'avant garde");
                            playerInTurret.remove(player);
                            return;
                        }
                        if (playerInTurret.containsKey(player))
                            playerInTurret.put(player, playerInTurret.get(player) + 1);
                        else
                            playerInTurret.put(player, 1);
                        if(playerInTurret.get(player) == 1)
                            player.sendMessage(ChatColor.BLUE + "[Avant-Garde]" + ChatColor.YELLOW + "Restez encore 10 secondes dans le perimetre de l'avant garde pour le capturer");
                    }else
                        playerInTurret.remove(player);
                }
            }
        }.runTaskTimer(wasteland.getMain(),20,20);
    }

    public void shoot (Location location){
        Vector vector = location.clone().subtract(this.location).toVector().normalize().multiply(0.3);
        for (int n = (int) location.distance(this.location) * 10/3; n >= 0; n--) {
            this.location.add(vector);
            location.getWorld().spawnParticle(Particle.FLAME , this.location.getX(),this.location.getY(),this.location.getZ(),1,0,0,0,0);
        }
    }

    public int getTeamInTurret(){
        int playerIntTurret = 0;
        for(Player player : Bukkit.getOnlinePlayers()){
            WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
            if(wastelandPlayer.getTeam() == this.team && player.getLocation().distance(this.location) <= range)
                playerIntTurret++;
        }
        return playerIntTurret;
    }

    public int getEnnemiesInTurret(){
        int playerIntTurret = 0;
        for(Player player : Bukkit.getOnlinePlayers()){
            WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
            if(wastelandPlayer.getTeam() != this.team && player.getLocation().distance(this.location) <= range)
                playerIntTurret++;
        }
        return playerIntTurret;
    }

    public void disable(){
        bukkitRunnable.cancel();
    }

    public void init(){
        location.getBlock().setType(Material.AIR);

    }

}

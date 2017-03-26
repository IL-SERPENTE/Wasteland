package net.samagames;

import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.entity.Turret;
import net.samagames.player.Team;
import net.samagames.player.TeamColor;
import net.samagames.player.WastelandPlayer;
import net.samagames.tools.Area;
import net.samagames.tools.LocationUtils;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by werter on 01.03.2017.
 */
public class Wasteland extends Game<WastelandPlayer> {

    private HashMap<Player,WastelandPlayer> registeredPlayer;
    private boolean isStarted = false;
    private Team teamRed;
    private Team teamBlue;
    private Wasteland instance;
    private WastelandMain wastelandMain;
    private Location spawn;
    private BukkitRunnable timerRunnable;

    public Wasteland(String gameCodeName, String gameName, String gameDescription, Class gamePlayerClass, WastelandMain main) {
        super(gameCodeName, gameName, gameDescription, gamePlayerClass);
        this.instance = this;
        this.wastelandMain = main;
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
        this.teamBlue = new Team(getInstance(), TeamColor.BLUE,LocationUtils.str2loc(object.get("spawn_blue").getAsString()),LocationUtils.str2loc(object.get("chest_blue").getAsString()));
        this.teamRed = new Team(getInstance(),TeamColor.RED,LocationUtils.str2loc(object.get("spawn_red").getAsString()),LocationUtils.str2loc(object.get("chest_red").getAsString()));
        Location loc = LocationUtils.str2loc(object.get("spawn").getAsString());
        this.spawn = loc;
        registeredPlayer = new HashMap<Player,WastelandPlayer>();
        teamBlue.setEnnemies(teamRed);
        teamRed.setEnnemies(teamBlue);
    }

    @Override
    public void handleLogin(Player player){
        super.handleLogin(player);
        player.teleport(getSpawn());
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setSaturation(20);
        player.setGameMode(GameMode.ADVENTURE);
        registerPlayer(player);
        for(WastelandItem item : WastelandItem.values())
            if (item.isStarterItem()){
                player.getInventory().setItem(item.getSlot(),item.getItemStack());
        }
        if(Bukkit.getOnlinePlayers().size() >= 8 && !isStarted())
            startGame();
    }

    @Override
    public void startGame() {
        super.startGame();
        new BukkitRunnable() {
            int cooldown = 10;
            @Override
            public void run() {
                if (cooldown == 60 || cooldown == 30 || cooldown <= 10)
                    if (cooldown == 0){
                        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStart();
                        JsonObject jsonObject = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.getInventory().clear();
                            if (!teamBlue.contains(player) && !teamRed.contains(player))
                                if (teamBlue.getMember().size() >= teamRed.getMember().size())
                                    setTeamRed(player);
                                else
                                    setTeamBlue(player);
                                if(teamRed.contains(player))
                                    player.teleport(LocationUtils.str2loc(jsonObject.get("spawn_red").getAsString()));
                                else
                                    player.teleport(LocationUtils.str2loc(jsonObject.get("spawn_blue").getAsString()));
                        }

                        isStarted = true;
                        start();
                        this.cancel();
                    }else
                    SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStartIn(cooldown);
                cooldown--;
            }
        }.runTaskTimer(getInstance().getMain(),20 ,20);
    }

    public void start(){
        timer();
        teamBlue.initScoreBoard();
        teamRed.initScoreBoard();
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
        Turret turret = new Turret(getInstance(),getTeamBlue(),LocationUtils.str2loc(object.get("turret_north_west").getAsString()),50);
        turret.init();
        turret.enable();
        Area harvestArea = new Area(LocationUtils.str2loc(object.get("harvest_area_first").getAsString()),LocationUtils.str2loc(object.get("harvest_area_second").getAsString()));
        new BukkitRunnable(){
            @Override
            public void run() {
                int randomX = ThreadLocalRandom.current().nextInt(harvestArea.getMin().getBlockX(),harvestArea.getMax().getBlockX() + 1);
                int randomZ =ThreadLocalRandom.current().nextInt(harvestArea.getMin().getBlockZ(),harvestArea.getMax().getBlockZ() + 1);
                Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"),randomX,harvestArea.getMax().getY(),randomZ), new ItemStack(Material.WHEAT));
            }
        }.runTaskTimer(getMain(),20,6);
    }

    public void timer () {
        timerRunnable = new BukkitRunnable() {
            int minutes = 0,seconds = 0;
            @Override
            public void run() {
                if(seconds < 59)
                    seconds++;
                else{
                    minutes++;
                    seconds = 0;
                }
                String minutesString = String.valueOf(minutes),secondsString = String.valueOf(seconds);
                if(minutes < 10)
                    minutesString = "0"+minutesString;
                if(seconds < 10)
                    secondsString = "0"+secondsString;
                for(Player player : Bukkit.getOnlinePlayers()){
                    WastelandPlayer wastelandPlayer = getWastelandPlayer(player);
                    wastelandPlayer.getScoreBoard().setLine(5, minutesString+":"+secondsString);
                    wastelandPlayer.getScoreBoard().updateLines();
                }
            }
        };
        timerRunnable.runTaskTimer(getMain(),20,20);
    }

    public Team getTeamRed (){
        return  this.teamRed;
    }

    public Team getTeamBlue() {
        return teamBlue;
    }

    public boolean isStarted(){
        return this.isStarted;
    }

    public void setTeamBlue(Player player){
        if(teamBlue.contains(player)){
            player.sendMessage(ChatColor.YELLOW + "Vous êtes déjà dans l'équipe" +ChatColor.BLUE + " bleu");
            return;
        }
        if(teamRed.getMember().size() >= teamBlue.getMember().size() && teamBlue.getMember().size() - teamRed.getMember().size() != 2){
            if(teamRed.contains(player)) teamRed.removePlayer(player);
            teamBlue.addPlayer(player);
            ActionBarAPI.sendPermanentMessage(player,ChatColor.GRAY + "Vous êtes dans l'équipe" + ChatColor.YELLOW + " : " + ChatColor.BLUE + "bleue");
        }else
            player.sendMessage(ChatColor.RED + "Il y a trop de joueur dans cette équipe");
    }

    public void setTeamRed(Player player) {
        if(teamRed.contains(player)){
            player.sendMessage(ChatColor.YELLOW + "Vous êtes déjà dans l'équipe" +ChatColor.RED + " rouge");
            return;
        }
        if(teamRed.getMember().size() <= teamBlue.getMember().size() && teamRed.getMember().size() - teamBlue.getMember().size() != 2){
            if(teamBlue.contains(player)) teamBlue.removePlayer(player);
            teamRed.addPlayer(player);
            ActionBarAPI.sendPermanentMessage(player,ChatColor.GRAY + "Vous êtes dans l'équipe" + ChatColor.YELLOW + " : " + ChatColor.RED + "rouge");
        }else
            player.sendMessage(ChatColor.RED + "Il y a trop de joueur dans cette équipe");
    }
    public WastelandPlayer getWastelandPlayer(Player player){
        return this.registeredPlayer.get(player);
    }

    public HashMap<Player, WastelandPlayer> getRegisteredPlayer() {
        return registeredPlayer;
    }

    public void registerPlayer(Player player){
        registeredPlayer.put(player, new WastelandPlayer(player));
    }

    public Location getSpawn(){
        return this.spawn;
    }

    public Wasteland getInstance(){
        return this.instance;
    }

    public WastelandMain getMain(){ return this.wastelandMain;}
}

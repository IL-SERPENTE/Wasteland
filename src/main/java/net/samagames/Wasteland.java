package net.samagames;

import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.api.games.Status;
import net.samagames.entity.PlantType;
import net.samagames.entity.Turret;
import net.samagames.player.Kit;
import net.samagames.player.Team;
import net.samagames.player.TeamColor;
import net.samagames.player.WastelandPlayer;
import net.samagames.tools.Area;
import net.samagames.tools.LocationUtils;
import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by werter on 01.03.2017.
 */
public class Wasteland extends Game<WastelandPlayer> {

    private HashMap<Player, WastelandPlayer> registeredPlayer;
    private Kit kitDefault,kitDefender,kitDemolisher,kitHerbelist,kitRobber,kitTrapper;
    private Team teamRed;
    private Team teamBlue;
    private WastelandMain wastelandMain;
    private Location spawn;
    private BukkitRunnable timerRunnable;
    private ArrayList<Location> locations = new ArrayList<>();


    public Wasteland(String gameCodeName, String gameName, String gameDescription, Class gamePlayerClass, WastelandMain main) {
        super(gameCodeName, gameName, gameDescription, gamePlayerClass);
        this.wastelandMain = main;
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
        this.teamBlue = new Team(getInstance(), TeamColor.BLUE, LocationUtils.str2loc(object.get("spawn_blue").getAsString()), LocationUtils.str2loc(object.get("chest_blue").getAsString()));
        this.teamBlue.setScoreBoard(new ObjectiveSign(this.teamBlue.getTeamColor().name(),  ChatColor.YELLOW + "" + ChatColor.BOLD +  "♨ Wasteland ♨"));
        this.teamRed = new Team(getInstance(), TeamColor.RED, LocationUtils.str2loc(object.get("spawn_red").getAsString()), LocationUtils.str2loc(object.get("chest_red").getAsString()));
        this.teamRed.setScoreBoard(new ObjectiveSign(this.teamRed.getTeamColor().name(),  ChatColor.YELLOW + "" + ChatColor.BOLD +  "♨ Wasteland ♨"));
        this.spawn = LocationUtils.str2loc(object.get("spawn").getAsString());
        this.kitDefault = Kit.DEFAULT;
        this.kitDefender = Kit.DEFENDER;
        this.kitRobber = Kit.ROBBER;
        this.kitDemolisher = Kit.DEMOLISHER;
        this.kitTrapper = Kit.TRAPPER;
        this.kitHerbelist = Kit.HERBALIST;
        this.registeredPlayer = new HashMap<>();
        teamBlue.setEnnemies(teamRed);
        teamRed.setEnnemies(teamBlue);
    }

    @Override
    public void handleLogin(Player player) {
        super.handleLogin(player);

        player.setExp(0);
        player.setLevel(0);

        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        player.teleport(getSpawn());
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setSaturation(20);
        player.setGameMode(GameMode.SURVIVAL);
        registerPlayer(player);
        for (WastelandItem item : WastelandItem.values())
            if (item.isStarterItem()) {
                player.getInventory().setItem(item.getSlot(), item.getItemStack());
            }
            getWastelandPlayer(player).setKit(getKitDefault(),false);
        if (Bukkit.getOnlinePlayers().size() >= 8 && !this.isGameStarted())
            startGame();

    }
    @Override
    public void startGame() {
        super.startGame();

        this.setStatus(Status.STARTING);

        new BukkitRunnable() {
            int cooldown = 10;

            @Override
            public void run() {
                if (cooldown == 60 || cooldown == 30 || cooldown <= 10)
                    if (cooldown == 0) {
                        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStart();
                        JsonObject jsonObject = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.getInventory().clear();
                            if (!teamBlue.contains(player) && !teamRed.contains(player))
                                if (teamBlue.getMember().size() >= teamRed.getMember().size())
                                    setTeamRed(player);
                                else
                                    setTeamBlue(player);
                            if (teamRed.contains(player))
                                player.teleport(LocationUtils.str2loc(jsonObject.get("spawn_red").getAsString()));
                            else
                                player.teleport(LocationUtils.str2loc(jsonObject.get("spawn_blue").getAsString()));
                        }

                        getInstance().setStatus(Status.IN_GAME);
                        start();
                        this.cancel();
                    } else
                        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStartIn(cooldown);
                cooldown--;
            }
        }.runTaskTimer(getInstance().getMain(), 20, 20);
    }

    public void start() {
        timer();
        teamBlue.initGame();
        teamRed.initGame();
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();

        String[] turrets = {"turret_south_west","turret_north_west","turret_north_est","turret_south_est"};
        for(String string : turrets){
            String[] locationPlant = object.get(string + "_plant").getAsString().split("/");
            Team team;
            if(string.contains("west"))
                team = getTeamBlue();
            else
                team = getTeamRed();
            Turret turret = new Turret(getInstance(), team, LocationUtils.str2loc(object.get(string).getAsString()), 25,LocationUtils.str2loc(locationPlant[0]),LocationUtils.str2loc(locationPlant[1]));
            turret.init();
            turret.enable();
        }

        Area harvestArea = new Area(LocationUtils.str2loc(object.get("harvest_area_first").getAsString()), LocationUtils.str2loc(object.get("harvest_area_second").getAsString()));

        Location max = harvestArea.getMax();
        int maxX = max.getBlockX();
        int maxY = max.getBlockY();
        int maxZ = max.getBlockZ();
        Location min = harvestArea.getMin();
        int minX = min.getBlockX();
        int minY = min.getBlockY();
        int minZ = min.getBlockZ();
        for(int x = minX; x <= maxX; x++)
            for(int z = minZ; z <= maxZ; z++)
                for(int y = minY; y <= maxY; y++) {
                    Location location = new Location(Bukkit.getWorld("world"),x,y,z);
                    if(location.getBlock().getType().equals(Material.SOIL) && location.add(0,1,0).getBlock().getType().equals(Material.AIR))
                        locations.add(location);
                }

        new BukkitRunnable() {
            @Override
            public void run() {
                Location location = locations.get(new Random().nextInt(locations.size()));
                location.getBlock().setType(Material.CROPS);
                locations.remove(location);
            }
        }.runTaskTimer(getMain(), 20, 6);
    }

    public void timer() {
        timerRunnable = new BukkitRunnable() {
            int minutes = 0, seconds = 0;
            @Override
            public void run() {
                if (seconds < 59)
                    seconds++;
                else {
                    minutes++;
                    seconds = 0;
                }
                String minutesString = String.valueOf(minutes), secondsString = String.valueOf(seconds);
                if (minutes < 10)
                    minutesString = "0" + minutesString;
                if (seconds < 10)
                    secondsString = "0" + secondsString;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    WastelandPlayer wastelandPlayer = getWastelandPlayer(player);
                    wastelandPlayer.getScoreBoard().setLine(10, minutesString + ":" + secondsString);
                    wastelandPlayer.getScoreBoard().updateLines();
                }
                if(minutes == 15) {
                    this.cancel();
                }
            }
        };
        timerRunnable.runTaskTimer(getMain(), 20, 20);
    }

    public Team getTeamRed() {
        return this.teamRed;
    }

    public Team getTeamBlue() {
        return teamBlue;
    }

    public void addLocation (Location location){
        this.locations.add(location);
    }

    public void setTeamBlue(Player player) {
        if (teamBlue.contains(player)) {
            player.sendMessage(ChatColor.YELLOW + "Vous êtes déjà dans l'équipe" + ChatColor.BLUE + " bleu");
            return;
        }
        if (teamRed.getMember().size() >= teamBlue.getMember().size() && teamBlue.getMember().size() - teamRed.getMember().size() != 2) {
            if (teamRed.contains(player)) teamRed.removePlayer(player);
            teamBlue.addPlayer(player);
            player.sendMessage(ChatColor.YELLOW + "Vous avez rejoint la team " + ChatColor.BLUE + "bleue");

        } else
            player.sendMessage(ChatColor.RED + "Il y a trop de joueur dans cette équipe");
    }

    public void setTeamRed(Player player) {
        if (teamRed.contains(player)) {
            player.sendMessage(ChatColor.YELLOW + "Vous êtes déjà dans l'équipe" + ChatColor.RED + " rouge");
            return;
        }
        if (teamRed.getMember().size() <= teamBlue.getMember().size() && teamRed.getMember().size() - teamBlue.getMember().size() != 2) {
            if (teamBlue.contains(player)) teamBlue.removePlayer(player);
            teamRed.addPlayer(player);
            player.sendMessage(ChatColor.YELLOW + "Vous avez rejoint la team " + ChatColor.RED + "rouge");
        } else
            player.sendMessage(ChatColor.RED + "Il y a trop de joueur dans cette équipe");
    }


    public void playEffect(Player sender,Team team, PlantType plantType){
        if(!plantType.isBonus())
            sender.sendMessage("Vous avez inflligé " + plantType.getPotionEffect().getDuration()/20 + " de " + plantType.getPotionEffect().getType().getName()  );
        for(Player player : team.getMember()) {
            player.addPotionEffect(plantType.getPotionEffect());
            player.sendMessage("Vous avez reçu " + plantType.getPotionEffect().getDuration()/20 + " de " + plantType.getPotionEffect().getType().getName() +  " au niveau " + plantType.getPotionEffect().getAmplifier());
        }
        if(new Random().nextInt(4) == 0)
            for (Player player : team.getEnnemies().getMember()) {
                player.addPotionEffect(plantType.getPotionEffect());
                player.sendMessage("Vous avez reçu " + plantType.getPotionEffect().getDuration()/20 + " de " + plantType.getPotionEffect().getType().getName() +  " au niveau " + plantType.getPotionEffect().getAmplifier());
            }
    }

    public Kit getKitDefault() {
        return kitDefault;
    }

    public Kit getKitDefender() {
        return kitDefender;
    }

    public Kit getKitDemolisher() {
        return kitDemolisher;
    }

    public Kit getKitHerbelist() {
        return kitHerbelist;
    }

    public Kit getKitRobber() {
        return kitRobber;
    }

    public Kit getKitTrapper() {
        return kitTrapper;
    }

    public Wasteland getInstance(){
	return this;
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

    public WastelandMain getMain(){ return this.wastelandMain;}
}

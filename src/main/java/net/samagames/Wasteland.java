package net.samagames;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.api.games.GamePlayer;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by werter on 01.03.2017.
 */
public class Wasteland extends Game<GamePlayer> {

    private boolean isStarted = false;
    private ArrayList<Player> teamRed;
    private ArrayList<Player> teamBlue;
    private Wasteland instance;
    private WastelandMain wastelandMain;

    public Wasteland(String gameCodeName, String gameName, String gameDescription, Class gamePlayerClass, WastelandMain main) {
        super(gameCodeName, gameName, gameDescription, gamePlayerClass);
        this.instance = this;
        this.teamBlue = new ArrayList<>();
        this.teamRed = new ArrayList<>();
        this.wastelandMain = main;
    }

    @Override
    public void handleLogin(Player player){
        super.handleLogin(player);
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setSaturation(1);
        player.setGameMode(GameMode.ADVENTURE);
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
        isStarted = true;
        new BukkitRunnable() {
            int cooldown = 60;
            @Override
            public void run() {
                if (cooldown == 60 || cooldown == 30 || cooldown <= 10)
                    if (cooldown == 0){
                        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStart();
                        for(Player player : Bukkit.getOnlinePlayers())
                            if(!teamBlue.contains(player) && !teamRed.contains(player))
                                if(teamBlue.size() >= teamRed.size())
                                    setTeamRed(player);
                                else
                                    setTeamBlue(player);
                        this.cancel();
                    }else
                    SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeGameStartIn(cooldown);
                cooldown--;
            }
        }.runTaskTimer(getInstance().getMain(),20 ,20);
    }

    public boolean isStarted(){
        return this.isStarted;
    }

    public void setTeamBlue(Player player){
        if(teamBlue.contains(player)){
            player.sendMessage(ChatColor.YELLOW + "Vous êtes déjà dans l'équipe" +ChatColor.BLUE + "bleu");
            return;
        }
        if(teamRed.size() >= teamBlue.size() && teamBlue.size() - teamRed.size() != 2){
            if(teamRed.contains(player)) teamRed.remove(player);
            teamBlue.add(player);
            ActionBarAPI.sendPermanentMessage(player,ChatColor.GRAY + "Vous êtes dans l'équipe" + ChatColor.YELLOW + " : " + ChatColor.BLUE + "bleue");
        }else
            player.sendMessage(ChatColor.RED + "Il y a trop de joueur dans cette équipe");
    }

    public void setTeamRed(Player player) {
        if(teamRed.contains(player)){
            player.sendMessage(ChatColor.YELLOW + "Vous êtes déjà dans l'équipe" +ChatColor.RED + "rouge");
            return;
        }
        if(teamRed.size() <= teamBlue.size() && teamRed.size() - teamBlue.size() != 2){
            if(teamBlue.contains(player)) teamBlue.remove(player);
            teamRed.add(player);
            ActionBarAPI.sendPermanentMessage(player,ChatColor.GRAY + "Vous êtes dans l'équipe" + ChatColor.YELLOW + " : " + ChatColor.RED + "rouge");
        }else
            player.sendMessage(ChatColor.RED + "Il y a trop de joueur dans cette équipe");
    }

    public Wasteland getInstance(){
        return this.instance;
    }

    public WastelandMain getMain(){ return this.wastelandMain;}
}

package net.samagames;

import net.samagames.Listerner.CancelledEvent;
import net.samagames.Listerner.PlayerEvent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.GamePlayer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by werter on 04.03.2017.
 */
public class WastelandMain extends JavaPlugin {
    @Override
    public void onEnable(){
        Wasteland wasteland=  new Wasteland("gameCode", "Wasteland", "by UnJoueur", GamePlayer.class);
        this.getServer().getPluginManager().registerEvents(new CancelledEvent(wasteland.getInstance()), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvent(wasteland.getInstance()),this);
        SamaGamesAPI.get().getGameManager().setFreeMode(true);
        SamaGamesAPI.get().getGameManager().registerGame(wasteland);
    }

    public WastelandMain getInstance(){
        return this;
    }
}

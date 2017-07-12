package net.samagames;

import net.samagames.Listerner.CancelledEvent;
import net.samagames.Listerner.PlayerEvent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.GamePlayer;
import net.samagames.api.games.Status;
import net.samagames.entity.Turret;
import org.bukkit.plugin.java.JavaPlugin;



/**
 * Created by werter on 04.03.2017.
 */
public class WastelandMain extends JavaPlugin {

    private Wasteland wasteland;

    @Override
    public void onEnable(){
        wasteland =  new Wasteland("gameCode", "Wasteland", "by Werter", GamePlayer.class, this);
        SamaGamesAPI.get().getGameManager().registerGame(wasteland);
        wasteland.setStatus(Status.WAITING_FOR_PLAYERS);
        SamaGamesAPI.get().getGameManager().setMaxReconnectTime(1);
        SamaGamesAPI.get().getGameManager().setLegacyPvP(true);
        this.getServer().getPluginManager().registerEvents(new CancelledEvent(wasteland.getInstance()), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvent(wasteland.getInstance()),this);
        this.getServer().getPluginManager().registerEvents(new Turret(),this);
    }


    public Wasteland getInstance() {return wasteland;}

    public WastelandMain getMain(){
        return this;
    }
}

package net.samagames;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.api.games.GamePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by werter on 01.03.2017.
 */
public class Wasteland extends Game<GamePlayer> {

    private boolean isStarted = false;
    private Wasteland instance;

    public Wasteland(String gameCodeName, String gameName, String gameDescription, Class gamePlayerClass) {
        super(gameCodeName, gameName, gameDescription, gamePlayerClass);
        this.instance = this;
    }

    @Override
    public void handleLogin(Player player){
        super.handleLogin(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        for(WastelandItem item : WastelandItem.values())
            if (item.isStarterItem()){
                player.getInventory().setItem(item.getSlot(),item.getItemStack());
        }
    }

    @Override
    public void startGame() {
        super.startGame();
        isStarted = true;
    }

    public boolean isStarted(){
        return this.isStarted;
    }

    public Wasteland getInstance(){
        return this.instance;
    }
}

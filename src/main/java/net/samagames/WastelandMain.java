package net.samagames;

import net.samagames.Listerner.CancelledEvent;
import net.samagames.Listerner.PlayerEvent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.GamePlayer;
import net.samagames.api.games.Status;
import net.samagames.entity.Turret;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This file is part of Wasteland.
 *
 * Wasteland is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Wasteland is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Wasteland.  If not, see <http://www.gnu.org/licenses/>.
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

package net.samagames.Listerner;

import net.samagames.Wasteland;
import net.samagames.WastelandItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by werter on 08.03.2017.
 */
public class PlayerEvent implements Listener {
    Wasteland wasteland;

    public PlayerEvent (Wasteland wasteland){
        this.wasteland = wasteland;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!wasteland.isStarted() && event.hasItem()){
            event.setCancelled(true);
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if(item.equals(WastelandItem.JOIN_TEAM_BLUE.getItemStack())){
                wasteland.setTeamBlue(player);
            }
            if(item.equals(WastelandItem.JOIN_TEAM_RED.getItemStack())) {
                wasteland.setTeamRed(player);
            }
        }
    }
}

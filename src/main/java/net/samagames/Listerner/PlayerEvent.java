package net.samagames.Listerner;

import net.samagames.Wasteland;
import net.samagames.WastelandItem;
import net.samagames.player.WastelandPlayer;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
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
    public void onPlayerPickupItem(PlayerPickupItemEvent event){
        event.setCancelled(true);
        Player player = event.getPlayer();
        WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
        int wheat = wastelandPlayer.getWheat();
        int amount = event.getItem().getItemStack().getAmount();
        if(wheat < 50) {
            event.getItem().remove();
            if (wheat + amount > 50) {
                wastelandPlayer.setWheat(50);
                ItemStack itemStack = event.getItem().getItemStack();
                itemStack.setAmount(wheat + amount - 50);
                player.getWorld().dropItem(event.getItem().getLocation(), itemStack);
            } else
                wastelandPlayer.addWheat(amount);
        }
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

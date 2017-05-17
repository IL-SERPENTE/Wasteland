package net.samagames.Listerner;

import net.samagames.Wasteland;
import net.samagames.player.WastelandPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by werter on 08.03.2017.
 */
public class CancelledEvent implements Listener {
    Wasteland wasteland;

    public CancelledEvent (Wasteland wasteland){
        this.wasteland = wasteland;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getBlockPlaced().getType().equals(Material.DOUBLE_PLANT) || event.getBlockPlaced().getType().equals(Material.RED_ROSE))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof  Player && event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof Player){
            Player shooter = (Player)((Projectile) event.getDamager()).getShooter();
            Player player = (Player) event.getEntity();
            if(wasteland.getWastelandPlayer(player).getTeam().equals(wasteland.getWastelandPlayer(shooter).getTeam()))
                event.setCancelled(true);
        }
        if(event.getDamager() instanceof Player && event.getEntity() instanceof  Player){
            WastelandPlayer player = wasteland.getWastelandPlayer((Player) event.getEntity());
            WastelandPlayer damager =  wasteland.getWastelandPlayer((Player) event.getDamager());
            if(player.getTeam().equals(damager.getTeam()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        if(!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!wasteland.isGameStarted() || event.getBlock().getType().equals(Material.SOIL))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if(!wasteland.hasPlayer(event.getPlayer()))
            return;
        if(!wasteland.isGameStarted())
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event){
        if(!wasteland.isGameStarted())
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if(!wasteland.isGameStarted())
            event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteracte(PlayerInteractEvent event){
        if(event.getAction().equals(Action.PHYSICAL)) event.setCancelled(true);
    }
}

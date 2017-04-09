package net.samagames.Listerner;

import net.samagames.Wasteland;
import net.samagames.WastelandItem;
import net.samagames.api.SamaGamesAPI;
import net.samagames.entity.Plant;
import net.samagames.entity.PlantType;
import net.samagames.player.WastelandPlayer;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;


/**
 * Created by werter on 08.03.2017.
 */
public class PlayerEvent implements Listener {
    Wasteland wasteland;

    public PlayerEvent (Wasteland wasteland){
        this.wasteland = wasteland;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event){
        if(!wasteland.hasPlayer(event.getPlayer()))
            return;
        if(!SamaGamesAPI.get().getGameManager().getGame().isGameStarted()){
            if (event.getPlayer().getInventory().getItem(event.getNewSlot()) == null){
                ActionBarAPI.sendMessage(event.getPlayer(), "");
                return;
            }
            Player player = event.getPlayer();
            ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
            for(WastelandItem wastelandItem : WastelandItem.values())
                if(wastelandItem.getName().equals(itemStack.getItemMeta().getDisplayName())){
                    ActionBarAPI.sendPermanentMessage(player,wastelandItem.getLore());
                    break;
                }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if(event.getPlayer().getInventory().contains(event.getItem().getItemStack()))
            if(event.getItem().getType().equals(Material.RED_ROSE) || event.getItem().getType().equals(Material.DOUBLE_PLANT)){
                event.setCancelled(true);
                event.getPlayer().sendMessage("Vous avez déjà cette fleur dans votre inventaire. Utilisez la pour pouvoir la rammaser");
            }
        if(!wasteland.hasPlayer(event.getPlayer()))
            return;
        if (event.getItem().getItemStack().getType().equals(Material.WHEAT)) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
            int wheat = wastelandPlayer.getWheat();
            int amount = event.getItem().getItemStack().getAmount();
            if (wheat < 50) {
                event.getItem().remove();
                if (wheat + amount > 50) {
                    wastelandPlayer.setWheat(50);
                    ItemStack itemStack = event.getItem().getItemStack();
                    itemStack.setAmount(wheat + amount - 50);
                    player.getWorld().dropItem(event.getItem().getLocation(), itemStack);
                } else {
                    wastelandPlayer.addWheat(amount);
                    player.playSound(player.getLocation(),Sound.ITEM_HOE_TILL,(float) 0.5,(float) 0.5);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getCurrentItem().getType().equals(Material.AIR))
            return;
        if(!SamaGamesAPI.get().getGameManager().getGame().isGameStarted()) {
            event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                player.closeInventory();
                WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
                ItemStack item = event.getCurrentItem();
                if(item.equals(WastelandItem.JOIN_TEAM_BLUE.getItemStack())) {
                    wasteland.setTeamBlue(player);
                    return;
                }
                if(item.equals(WastelandItem.JOIN_TEAM_RED.getItemStack())) {
                    wasteland.setTeamRed(player);
                    return;
                }
                if(item.equals(WastelandItem.KIT_SELECTOR.getItemStack())){
                    wastelandPlayer.openKitSelector();
                    return;
                }
                if(item.equals(WastelandItem.CHOOSE_KIT_DEFENDER.getItemStack())){
                    wastelandPlayer.setKit(wasteland.getKitDefender(),true);
                    return;
                }
                if(item.equals(WastelandItem.CHOOSE_KIT_DEMOLISHER.getItemStack())) {
                    wastelandPlayer.setKit(wasteland.getKitDemolisher(),true);
                    return;

                }
                if(item.equals(WastelandItem.CHOOSE_KIT_HERBALIST.getItemStack())) {
                    wastelandPlayer.setKit(wasteland.getKitHerbelist(),true);
                    return;
                }
                if(item.equals(WastelandItem.CHOOSE_KIT_ROBBER.getItemStack())) {
                    wastelandPlayer.setKit(wasteland.getKitRobber(),true);
                    return;
                }
                if(item.equals(WastelandItem.CHOOSE_KIT_TRAPPER.getItemStack())) {
                    wastelandPlayer.setKit(wasteland.getKitTrapper(),true);
                    return;
                }
        }
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        if(!wasteland.hasPlayer(event.getPlayer()))
            return;
        Player player = event.getPlayer();
        WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
        event.setRespawnLocation(wastelandPlayer.getTeam().getSpawn());
        player.setPassenger(wastelandPlayer.getArmorStand());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!wasteland.hasPlayer(event.getEntity()))
            return;
        if(event.getEntity() instanceof Player){
            Player player = event.getEntity();
            WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
            event.setKeepInventory(true);
            event.setDroppedExp(0);
            event.setDeathMessage(null);
            if(new Random().nextInt(50) == 3)
            new Plant(event.getEntity().getLocation()).spawn();
            if(wastelandPlayer.getWheat() > 0){
                event.setDeathMessage(player.getName()+ " est mort avec :" + wastelandPlayer.getWheat() + " blés sur lui");
                event.getDrops().clear();
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(),new ItemStack(Material.WHEAT,wastelandPlayer.getWheat()));
                //if(new Random().nextInt(50) == 3){
                //}
                wastelandPlayer.setWheat(0);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!wasteland.hasPlayer(event.getPlayer()))
            return;
        Player player = event.getPlayer();
        WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);

        //plant
        if(SamaGamesAPI.get().getGameManager().getGame().isGameStarted() && event.hasItem())
            if(event.getItem().getType().equals(Material.DOUBLE_PLANT) || event.getItem().getType().equals(Material.RED_ROSE)){
                for(PlantType plantType : PlantType.values())
                    if(plantType.getItemStack().equals(event.getItem())) {
                    if(plantType.isBonus())
                        wasteland.playEffect(wastelandPlayer.getTeam(),plantType);
                    else
                        wasteland.playEffect(wastelandPlayer.getTeam().getEnnemies(), plantType);
                    break;
                }
                player.getItemInHand().setType(Material.AIR);
            }

        // Harvest
        if(event.hasBlock())
            if(wastelandPlayer.hasTeam()) {
                if(wastelandPlayer.getTeam().getEnnemies().getChestLocation().equals(event.getClickedBlock().getLocation())){
                    //TODO ADD COOLDOWN AND RANDOM
                    event.setCancelled(true);
                    if(wastelandPlayer.getTeam().getEnnemies().getWheat() < 16){
                        player.sendMessage("L'équipe adverse n'as pas assez de ressources pour être volé");
                        return;
                    }
                    if(wastelandPlayer.getWheat() == 50){
                        player.sendMessage("Vous ne pouvez pas voler de ressource car vous êtes full");
                        return;
                    }
                    int capacity = 50 - wastelandPlayer.getWheat();
                    if(capacity > 15)
                        capacity = 15;
                    wastelandPlayer.getTeam().getEnnemies().removeWheat(capacity);
                    wastelandPlayer.addWheat(capacity);
                    player.sendMessage("Vous avez volé " + capacity + " blés");
                }
                if (wastelandPlayer.getTeam().getChestLocation().equals(event.getClickedBlock().getLocation())) {
                    wastelandPlayer.getTeam().addWheat(wastelandPlayer.getWheat());
                    wastelandPlayer.setWheat(0);
                    event.setCancelled(true);
                }
            }

            //team ands kit selector
        if(!SamaGamesAPI.get().getGameManager().getGame().isGameStarted() && event.hasItem()){
            event.setCancelled(true);
            ItemStack item = event.getItem();
            if(item.equals(WastelandItem.KIT_SELECTOR.getItemStack())){
                wastelandPlayer.openKitSelector();
            }
            if(item.equals(WastelandItem.JOIN_TEAM_BLUE.getItemStack())){
                wasteland.setTeamBlue(player);
            }
            if(item.equals(WastelandItem.JOIN_TEAM_RED.getItemStack())) {
                wasteland.setTeamRed(player);
            }
        }
    }
}

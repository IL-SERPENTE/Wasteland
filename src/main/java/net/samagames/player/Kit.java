package net.samagames.player;


import net.samagames.WastelandItem;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

/**
 * Created by werter on 07.04.2017.
 */
public class Kit {

    protected String name;
    protected Inventory playerInventory = Bukkit.createInventory(null, InventoryType.PLAYER);

    public void init(){
        Arrays.asList(SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs().get("starter"));

    }


    public String getName() {
        return name;
    }

    public Inventory getPlayerInventory(){
        return this.playerInventory;
    }

    public void equip(Player player){
        player.sendMessage("tu es" + name);
        player.getInventory().clear();
        player.getInventory().setContents(playerInventory.getContents());
    }
}

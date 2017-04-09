package net.samagames.player.kit;

import net.samagames.WastelandItem;
import net.samagames.player.Kit;
import org.bukkit.inventory.Inventory;

/**
 * Created by werter on 09.04.2017.
 */
public class  Demolisher extends Kit {
    public Demolisher (Inventory playerInventory,String name){
        this.name = name;
        this.playerInventory = playerInventory;
    }

}

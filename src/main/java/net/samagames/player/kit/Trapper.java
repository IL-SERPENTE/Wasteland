package net.samagames.player.kit;

import net.samagames.WastelandItem;
import net.samagames.player.Kit;
import org.bukkit.inventory.Inventory;

/**
 * Created by werter on 09.04.2017.
 */
public class Trapper extends Kit {
    public Trapper (Inventory playerInventory, String name){
        this.playerInventory = playerInventory;
        this.name = name;
    }
}

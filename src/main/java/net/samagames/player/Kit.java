package net.samagames.player;


import com.google.gson.Gson;
import net.samagames.api.SamaGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

/**
 * Created by werter on 07.04.2017.
 */
public class Kit {

    protected String name = "Default";
    protected Inventory playerInventory = Bukkit.createInventory(null, InventoryType.PLAYER);

    public void init(){
        this.playerInventory.addItem(new ItemStack(Material.IRON_SWORD),new ItemStack(Material.BOW),new ItemStack(Material.ARROW,10));
    }


    public String getName() {
        return name;
    }

    public Inventory getPlayerInventory(){
        return this.playerInventory;
    }

    public void equip(WastelandPlayer wastelandPlayer){
        Player player = wastelandPlayer.getPlayer();
        player.sendMessage("tu es " + name);
        player.getInventory().clear();
        player.getInventory().setContents(playerInventory.getContents());
        ItemStack[] itemStacks = {new ItemStack(Material.LEATHER_BOOTS),new ItemStack(Material.LEATHER_LEGGINGS),new ItemStack(Material.LEATHER_CHESTPLATE),new ItemStack(Material.LEATHER_HELMET)};
        for(ItemStack itemStack : itemStacks) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            ((LeatherArmorMeta)itemMeta).setColor(wastelandPlayer.getTeam().getTeamColor().getColor());
            itemStack.setItemMeta(itemMeta);
        }
        player.getInventory().setArmorContents(itemStacks);
    }
}

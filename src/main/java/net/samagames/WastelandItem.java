package net.samagames;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 04.03.2017.
 */
public enum WastelandItem {
    LEAVE(new ItemStack(Material.WOOD_DOOR),"Leave",8 ,true,"leave"),
    KIT_SELECTOR(new ItemStack(Material.ENDER_CHEST), "kit",4 ,true ,"kit"),
    JOIN_TEAM_BLUE(new ItemStack(Material.BANNER,1, (byte) 4),"Blue",0 ,true,"Blue"),
    JOIN_TEAM_RED(new ItemStack(Material.BANNER,1,(byte)1),"Red",1,true,"Red");


    private ItemStack itemStack;
    private String name;
    private int slot;
    private boolean starterItem;
    private String[] lore;


    WastelandItem(ItemStack itemStack ,String name,Integer slot ,boolean starterItem ,String... lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> listLore = new ArrayList<>();
        for(String stringLore : lore)
            listLore.add(stringLore);
        itemMeta.setLore(listLore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.name = name;
        this.lore = lore;
        this.slot = slot;
        this.starterItem = starterItem;
        this.itemStack = itemStack;
    }

    public boolean isStarterItem() {
        return starterItem;
    }

    public int getSlot() {
        return slot;
    }

    public String[] getLore() {
        return lore;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }
}

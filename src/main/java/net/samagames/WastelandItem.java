package net.samagames;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
public enum WastelandItem {

    LEAVE(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getLeaveItem(),"Leave",8 ,true,"leave"),
    KIT_SELECTOR(new ItemStack(Material.ENDER_CHEST), "kit",4 ,true ,"kit"),
    TEAM_SELECTOR(new ItemStack(Material.BANNER,1,(byte)15),"Choisir une équipe",0,true,"Cliquez pour rejoindre une équipe."),
    JOIN_TEAM_BLUE(new ItemStack(Material.BANNER,1, (byte) 4),ChatColor.BLUE + "Bleu",0 ,false,ChatColor.BLUE + "rejoindre léquipe bleu"),
    JOIN_TEAM_RED(new ItemStack(Material.BANNER,1,(byte)1),ChatColor.RED + "Rouge",4,false, ChatColor.RED + "rejoinde l'équipe rouge"),
    CHOOSE_KIT_DEFENDER(new ItemStack(Material.BOW), "Defenseur",9, false, "Ce kit vous permet d'avoir plus de fleche"),
    CHOOSE_KIT_DEMOLISHER(new ItemStack(Material.IRON_AXE), "Demolisseur",11 ,false , "Lorsque que le joueur ressuscite","sa résistance est augmentée, il","reçoit donc des coeurs d'absorptions","pendant 30 secondes."),
    CHOOSE_KIT_HERBALIST(new ItemStack(Material.LONG_GRASS),"Herboriste", 13 , false , "Lorsqu’un joueur tue un ennemi, la","chance qu’il a de recevoir","des plantes dans son inventaire","est augmenté."),
    CHOOSE_KIT_ROBBER(new ItemStack(Material.DOUBLE_PLANT), "Voleur", 15, false ,"Lorsque le joueur pille le camp","adverse, la chance qu’il","a de voler un plus grand nombre ","de ressource est augmenté."),
    CHOOSE_KIT_TRAPPER(new ItemStack(Material.WEB), "Trappeur", 17, false , "Vous avez plus de toiles d'araignées.");

    private ItemStack itemStack;
    private String name;
    private int slot;
    private boolean starterItem;
    private String lore;
    private String[] loreList;


    WastelandItem(ItemStack itemStack ,String name,Integer slot ,boolean starterItem ,String lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> listLore = new ArrayList<>();
        listLore.add(lore);
        itemMeta.setLore(listLore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.name = name;
        this.lore = lore;
        this.slot = slot;
        this.starterItem = starterItem;
        this.itemStack = itemStack;
    }
    WastelandItem(ItemStack itemStack ,String name,Integer slot ,boolean starterItem ,String... loreList){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> listLore = new ArrayList<>();
        for(String lore: loreList)
            listLore.add(lore);
        itemMeta.setLore(listLore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.name = name;
        this.loreList = loreList;
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

    public String getLore() {
        return lore;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }
}

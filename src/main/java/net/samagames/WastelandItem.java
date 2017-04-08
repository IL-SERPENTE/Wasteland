package net.samagames;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werter on 04.03.2017.
 */
public enum WastelandItem {

    LEAVE(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getLeaveItem(),"Leave",8 ,true,"leave"),
    KIT_SELECTOR(new ItemStack(Material.ENDER_CHEST), "kit",4 ,true ,"kit"),
    JOIN_TEAM_BLUE(new ItemStack(Material.BANNER,1, (byte) 4),ChatColor.BLUE + "Bleu",0 ,true,ChatColor.BLUE + "rejoindre léquipe bleu"),
    JOIN_TEAM_RED(new ItemStack(Material.BANNER,1,(byte)1),ChatColor.RED + "Rouge",1,true, ChatColor.RED + "rejoinde l'équipe rouge"),


    CHOOSE_KIT_DEFENDER(new ItemStack(Material.BOW), "Choisir le kit defenseur",9, false, "Ce kit vous permet d'avoir plus de fleche"),
    CHOOSE_KIT_DEMOLISHER(new ItemStack(Material.IRON_AXE), "Choisir le kit demolisseur",11 ,false , "Lorsque que le joueur ressuscite sa résistance est augmentée, il reçoit donc des coeurs d'absorptions pendant 30 secondes."),
    CHOOSE_KIT_HERBALIST(new ItemStack(Material.LONG_GRASS),"Choisir le kit Herboriste", 13 , false , "Lorsqu’un joueur tue un ennemi, la chance qu’il a de recevoir des plantes dans son inventaire est augmenté."),
    CHOOSE_KIT_ROBBER(new ItemStack(Material.YELLOW_FLOWER), "Choisir le kit Voleur", 15, false ,"Lorsque le joueur pille le camp adverse, la chance qu’il a de voler un plus grand nombre de ressource est augmenté."),
    CHOOSE_KIT_TRAPPER(new ItemStack(Material.WEB), "Choisir le kit trappeur", 17, false , "Vous avez plus de toiles d'araignées.");

    private ItemStack itemStack;
    private String name;
    private int slot;
    private boolean starterItem;
    private String lore;


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

    public static boolean isWastelandItem(ItemStack itemStack){
        boolean isWastelandItem = false;
        for(WastelandItem wastelandItem : WastelandItem.values())
            if(wastelandItem.getItemStack().equals(itemStack)){
                isWastelandItem = true;
                break;
            }
        return isWastelandItem;
    }

    public static WastelandItem getByItemStack(ItemStack itemStack){
        WastelandItem wastelandItem = null;
        for(WastelandItem wastelandItems : WastelandItem.values())
            if(wastelandItem.getItemStack().equals(itemStack)){
                wastelandItem = wastelandItems;
                break;
            }
        return  wastelandItem;
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

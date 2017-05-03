package net.samagames.player;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;


/**
 * Created by werter on 07.04.2017.
 */
public enum Kit {

    DEFAULT("Defaut"),
    DEFENDER("Defensseur"),
    DEMOLISHER("Demolisseur"),
    HERBALIST("Heboriste"),
    ROBBER("Voleur"),
    TRAPPER("Trappeur");


    private ItemStack[] itemStacks = {new ItemStack(Material.IRON_SWORD),new ItemStack(Material.BOW),new ItemStack(Material.ARROW,10),new ItemStack(Material.WEB)};
    private String name;
    private Inventory inventory;

    Kit(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Inventory getPlayerInventory(){
        return this.inventory;
    }

    public void equip(WastelandPlayer wastelandPlayer){
        Player player = wastelandPlayer.getPlayer();
        player.sendMessage("tu es " + name);
        player.getInventory().clear();
        player.getInventory().setContents(inventory.getContents());
        if(wastelandPlayer.getKit().equals(Kit.TRAPPER))
            player.getInventory().addItem(new ItemStack(Material.WEB,wastelandPlayer.getAmplifier()));
        if(wastelandPlayer.getKit().equals(Kit.DEFENDER))
            player.getInventory().addItem(new ItemStack(Material.ARROW,wastelandPlayer.getAmplifier()));
        ItemStack[] itemStacks = {new ItemStack(Material.LEATHER_BOOTS),new ItemStack(Material.LEATHER_LEGGINGS),new ItemStack(Material.LEATHER_CHESTPLATE),new ItemStack(Material.LEATHER_HELMET)};
        for(ItemStack itemStack : itemStacks) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            ((LeatherArmorMeta)itemMeta).setColor(wastelandPlayer.getTeam().getTeamColor().getColor());
            itemStack.setItemMeta(itemMeta);
            itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
        }
        player.getInventory().setArmorContents(itemStacks);
    }
}

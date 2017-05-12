package net.samagames.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by werter on 09.04.2017.
 */
public enum PlantType {
    JUMP_BOOST(new ItemStack(Material.RED_ROSE,1,(byte)3), "Daem Avium", Arrays.asList("Donne du jump boost a votre équipe !", "Mais il y a 20% de chance que l'équipe","adverse l'ai aussi."), true,0, new PotionEffect(PotionEffectType.JUMP,3*20,1)),
    REGENERATION(new ItemStack(Material.DOUBLE_PLANT), "Plantea Cura",Arrays.asList("Donne de la régéneration a votre équipe !", "Mais il y a 20% de chance que l'équipe","adverse l'ai aussi."), true,1, new PotionEffect(PotionEffectType.REGENERATION,2*20,2)),
    SPEED_BOOST(new ItemStack(Material.RED_ROSE,1,(byte)1),"Viatea Lucidum",Arrays.asList("Donne du speed a votre équipe !", "Mais il y a 20% de chance que l'équipe","adverse l'ai aussi."),true , 2, new PotionEffect(PotionEffectType.SPEED,3*20,2)),
    NAUSEA(new ItemStack(Material.DOUBLE_PLANT,1,(byte)4), "Florus Vomitus",Arrays.asList("Donne de la confusion a l'équipe adverse !", "Mais il y a 20% de chance que votre équipe","l'ait aussi.") , false,3, new PotionEffect(PotionEffectType.CONFUSION,3*20,1)),
    POISON(new ItemStack(Material.DOUBLE_PLANT,1,(byte)1), "Albus Venenum",Arrays.asList("Donne du poison a l'équipe adverse !", "Mais il y a 20% de chance que votre équipe","l'ait aussi.") , false,4, new PotionEffect(PotionEffectType.POISON, 2*20,2)),
    SLOWNESS(new ItemStack(Material.DOUBLE_PLANT,1,(byte) 5),"Magna Gravis", Arrays.asList("Donne du slowness a l'équipe adverse !", "Mais il y a 20% de chance que votre équipe","l'ait aussi."),false,5, new PotionEffect(PotionEffectType.SLOW, 3*20,2));

    private int id;
    private PotionEffect potionEffect;
    private ItemStack itemStack;
    private String name;
    private boolean isBonus;

    PlantType(ItemStack itemStack, String name, List lore, boolean isBonus, int id, PotionEffect potionEffect){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
        this.name = name;
        this.potionEffect = potionEffect;
        this.id = id;
        this.isBonus = isBonus;
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

    public ItemStack getItemStack(){
        return  this.itemStack;
    }

    public int getId() {
        return id;
    }

    public boolean isBonus() {
        return isBonus;
    }

    public String getName() {
        return name;
    }
}

package net.samagames.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by werter on 09.04.2017.
 */
public enum PlantType {
    JUMP_BOOST(new ItemStack(Material.RED_ROSE,1,(byte)3), "Daem Avium", true,0, new PotionEffect(PotionEffectType.JUMP,3*20,1)),
    REGENERATION(new ItemStack(Material.DOUBLE_PLANT), "Plantea Cura", true,1, new PotionEffect(PotionEffectType.REGENERATION,2*20,2)),
    SPEED_BOOST(new ItemStack(Material.RED_ROSE,1,(byte)1), "Viatea Lucidum", true , 2, new PotionEffect(PotionEffectType.SPEED,3*20,2)),
    NAUSEA(new ItemStack(Material.DOUBLE_PLANT,1,(byte)4), "Florus Vomitus", false,3, new PotionEffect(PotionEffectType.CONFUSION,3*20,1)),
    POISON(new ItemStack(Material.DOUBLE_PLANT,1,(byte)1), "Albus Venenum", false,4, new PotionEffect(PotionEffectType.POISON, 2*20,2)),
    SLOWNESS(new ItemStack(Material.DOUBLE_PLANT,1,(byte) 5), "Magna Gravis", false,5, new PotionEffect(PotionEffectType.SLOW, 3*20,2));

    private int id;
    private PotionEffect potionEffect;
    private ItemStack itemStack;
    private String name;
    private boolean isBonus;

    PlantType(ItemStack itemStack, String name, boolean isBonus, int id, PotionEffect potionEffect){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
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

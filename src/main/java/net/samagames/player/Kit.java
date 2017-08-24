package net.samagames.player;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
public enum Kit {

    DEFAULT("Defaut"),
    DEFENDER("Defensseur"),
    DEMOLISHER("Demolisseur"),
    HERBALIST("Heboriste"),
    ROBBER("Voleur"),
    TRAPPER("Trappeur");


    private ItemStack[] itemStacks = {new ItemStack(Material.IRON_SWORD),new ItemStack(Material.BOW),new ItemStack(Material.ARROW,10),new ItemStack(Material.WEB)};
    private String name;

    Kit(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void equip(WastelandPlayer wastelandPlayer){
        Player player = wastelandPlayer.getPlayer();
        player.sendMessage("tu es " + name);
        player.getInventory().clear();
        player.getInventory().setContents(this.itemStacks);
        if(wastelandPlayer.getKit().equals(Kit.DEMOLISHER))
            player.setMaxHealth(player.getMaxHealth() + wastelandPlayer.getAmplifier());
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

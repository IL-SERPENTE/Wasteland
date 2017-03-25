package net.samagames.player;

import org.bukkit.ChatColor;

/**
 * Created by werter on 21.03.2017.
 */
public enum TeamColor{

    RED(ChatColor.RED,"rouge"),BLUE(ChatColor.BLUE,"bleu");

    private ChatColor chatColor;
    private String name;

    TeamColor(ChatColor color,String name){
        this.chatColor = color;
        this.name = name;
    }

    public String getName() { return  this.name;}

    public ChatColor getChatColor() {
        return chatColor;
    }
}
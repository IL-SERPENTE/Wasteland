package net.samagames.player;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Created by werter on 21.03.2017.
 */
public enum TeamColor{

    RED(ChatColor.RED,Color.RED,"rouge"),BLUE(ChatColor.BLUE,Color.BLUE,"bleu");

    private ChatColor chatColor;
    private Color color;
    private String name;

    TeamColor(ChatColor chatColor, Color color, String name){
        this.chatColor = chatColor;
        this.color = color;
        this.name = name;
    }


    public String getName() { return  this.name;}

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Color getColor() {
        return color;
    }
}
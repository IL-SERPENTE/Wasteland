package net.samagames.player;

import org.bukkit.ChatColor;

/**
 * Created by werter on 21.03.2017.
 */
public enum TeamColor{

    RED(ChatColor.RED),BLUE(ChatColor.BLUE);

    private ChatColor chatColor;

    TeamColor(ChatColor color){
        this.chatColor = color;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
package net.samagames.player;

import org.bukkit.ChatColor;
import org.bukkit.Color;

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
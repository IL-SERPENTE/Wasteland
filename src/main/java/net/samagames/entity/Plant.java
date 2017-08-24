package net.samagames.entity;

import org.bukkit.Location;

import java.util.Random;

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
public class Plant {

    private Location location;
    private PlantType plantType;
    private boolean isBonus;

    public Plant (Location location){
        this.location = location;
        while (this.plantType == null)
            for(PlantType plantType : PlantType.values())
                if(new Random().nextInt(3) == 1) {
                    this.plantType = plantType;
                    break;
                }
    }

    public Plant (Location location, PlantType plantType){
        this.location = location;
        this.plantType = plantType;
        this.isBonus = plantType.isBonus();
    }

    public Plant (Location location, boolean isBonus){
        this.location = location;
        this.isBonus = isBonus;
        while (this.plantType == null)
        for(PlantType plantType : PlantType.values())
            if(new Random().nextInt(3) == 1 && plantType.isBonus() == isBonus) {
                this.plantType = plantType;
                break;
        }
    }

    public void spawn(){
        location.getWorld().dropItem(location,plantType.getItemStack());
    }
}

package net.samagames.entity;

import org.bukkit.Location;

import java.util.Random;

/**
 * Created by werter on 09.04.2017.
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

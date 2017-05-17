package net.samagames.player;

import net.samagames.Wasteland;
import net.samagames.WastelandItem;
import net.samagames.api.games.GamePlayer;
import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

/**
 * Created by werter on 21.03.2017.
 */
public class WastelandPlayer extends GamePlayer {

    private Player player;
    private Wasteland wasteland;
    private float walkSpeed;
    private ObjectiveSign scoreBoard;
    private Team team;
    private Kit kit;
    private int wheat;
    private int amplifier;

    public WastelandPlayer(Player player,Wasteland wasteland){
        super(player);
        this.player = player;
        this.wasteland = wasteland;
        this.walkSpeed = player.getWalkSpeed();
    }

    public void resetWalkSpeed(){
        player.setWalkSpeed(this.walkSpeed);
    }



    public ObjectiveSign getScoreBoard(){ return this.scoreBoard;}

    public void setScoreBoard(ObjectiveSign scoreBoard){
        this.scoreBoard = scoreBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public int getWheat(){
        return this.wheat;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean hasTeam(){
       return team != null;
    }

    public void setKit(Kit kit,boolean sendMessage){
         this.kit = kit;
         if(sendMessage)
            player.sendMessage(ChatColor.YELLOW + "Tu as pris le kit: " + ChatColor.GRAY + kit.getName());
         //TODO WITH SHOP IDs

        if(kit.equals(Kit.DEFENDER)) {
            this.amplifier = 6;
            return;
        }

        if(kit.equals(Kit.DEMOLISHER)){
            this.amplifier = 2;
            return;
        }

        if(kit.equals(Kit.ROBBER)){
            this.amplifier = 25;
            return;
        }

        if(kit.equals(Kit.HERBALIST)) {
            this.amplifier = 25;
            return;
        }

        if(kit.equals(Kit.TRAPPER)){
            amplifier = 2;
            return;
        }

    }

    public int getAmplifier(){
        return  this.amplifier;
    }

    public Kit getKit(){ return this.kit;}

    public boolean isInTeam(TeamColor color){
        boolean isInTeam = false;
        if(hasTeam())
            if(team.getTeamColor().equals(color))
                isInTeam = true;
        return isInTeam;
    }


    public void openKitSelector(){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER, "Séléctionnez votre kit");
        for(WastelandItem wastelandItem : WastelandItem.values()) {
            if(wastelandItem.getItemStack().getType().equals(Material.BANNER)){
                continue;
            }
            if (!wastelandItem.isStarterItem())
                inventory.setItem(wastelandItem.getSlot(), wastelandItem.getItemStack());
        }
        player.openInventory(inventory);
    }

    public void updateScoreBoard(){
	if(this.team == null) return;
        if(player.getScoreboard() != null) {
            getScoreBoard().setLine(6, "Sur vous :" + getWheat());
            getScoreBoard().updateLines();
        }
    }

    public void setWheat(int wheat) {
	if(this.team == null) return;
        this.wheat = wheat;

        updateScoreBoard();

        player.setLevel(wheat);

        if(wheat == 50){
            player.sendMessage(ChatColor.RED + "Vous ne pouvez plus rammaser de blés");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,(float) 1 , (float) 1);
        }
    }

    public void addWheat(int number){
	if(this.team == null) return;
        setWheat(wheat + number);
        updateScoreBoard();

    }

    public void setBannerColor(DyeColor dyeColor){
        if(!wasteland.isGameStarted()){
            ItemStack itemStack = player.getInventory().getItem(0);
            BannerMeta bannerMeta = (BannerMeta) itemStack.getItemMeta();
            bannerMeta.setBaseColor(dyeColor);
            itemStack.setItemMeta(bannerMeta);
            player.getInventory().setItem(0,itemStack);
        }
    }

}

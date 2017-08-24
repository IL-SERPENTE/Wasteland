package net.samagames.player;

import net.samagames.Wasteland;
import net.samagames.entity.Turret;
import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
public class Team {

    private int wheat;
    private ObjectiveSign scoreBoard;
    private Team ennemies;
    private Location chestLocation,spawn;
    private Wasteland wasteland;
    private TeamColor teamColor;
    private List<Player> member;
    private Turret[] turrets;

    public Team(Wasteland wasteland,TeamColor color,Location spawn ,Location chestLocation){
        this.wasteland = wasteland;
        this.teamColor = color;
        this.spawn = spawn;
        this.chestLocation = chestLocation;
        this.member = new ArrayList<>();
    }

    public void updateScoreBoard(){
        Team[] teams = {wasteland.getTeamBlue(),wasteland.getTeamRed()};
        for(Team team : teams){
            ObjectiveSign objectiveSign = team.getScoreBoard();
            objectiveSign.setLine(7,"Votre coffre :" + team.getWheat());
            objectiveSign.setLine(8, "L'équipe adverse: " + team.getEnnemies().getWheat());
            objectiveSign.updateLines();
        }
    }

    public void initGame(){
        if(!this.member.isEmpty())
            for(Player player : this.member) {
                WastelandPlayer wastelandPlayer = wasteland.getWastelandPlayer(player);
                wastelandPlayer.setScoreBoard(wastelandPlayer.getTeam().getScoreBoard());
                ObjectiveSign objectiveSign = wastelandPlayer.getScoreBoard();
                objectiveSign.setLine(0, " ");
                objectiveSign.setLine(1,"Équipe: " + teamColor.getChatColor() + teamColor.getName());
                objectiveSign.setLine(2,"  ");
                objectiveSign.setLine(3,"Nombre de blés");
                objectiveSign.setLine(5, "   ");
                objectiveSign.setLine(6,"Sur vous : " + wastelandPlayer.getWheat());
                objectiveSign.setLine(7,"Votre coffre : " + wastelandPlayer.getTeam().getWheat());
                objectiveSign.setLine(8, "L'équipe adverse: " + wastelandPlayer.getTeam().getEnnemies().getWheat());
                objectiveSign.setLine(9, "      ");
                objectiveSign.setLine(10, "00:00");
                objectiveSign.addReceiver(player);
                wastelandPlayer.getKit().equip(wastelandPlayer);
            }
    }

    public void setEnnemies(Team ennemies) {
        this.ennemies = ennemies;
    }

    public Team getEnnemies() {
        return ennemies;
    }

    public void setScoreBoard (ObjectiveSign scoreBoard) { this.scoreBoard = scoreBoard;}

    public ObjectiveSign getScoreBoard() {
        return scoreBoard;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    public int getWheat(){return  this.wheat;}

    public void setWheat(int wheat) {
        this.wheat = wheat;
        updateScoreBoard();
    }

    public void removeWheat(int wheat){
        this.wheat = this.wheat - wheat;
        updateScoreBoard();
    }

    public void addWheat(int wheat){
        this.wheat = +wheat + this.wheat;
        updateScoreBoard();
    }

    public void setTurrets (Turret... turrets){
        this.turrets = turrets;
    }

    public Turret[] getTurrets() {
        return turrets;
    }

    public TeamColor getTeamColor(){
        return this.teamColor;
    }

    public void addPlayer(Player player){
        this.member.add(player);
        for(org.bukkit.scoreboard.Team team : player.getScoreboard().getTeams())
            if(team.getPlayers().contains(player)) {
                team.setPrefix(teamColor.getChatColor() + "[Équipe" + teamColor.getName() + "] ");
                break;
            }
        player.setPlayerListName(this.getTeamColor().getChatColor() + player.getName());
        wasteland.getWastelandPlayer(player).setTeam(this);
    }

    public void removePlayer(Player player){
        this.member.remove(player);

        player.setPlayerListName(this.getTeamColor().getChatColor() + player.getName());
        wasteland.getWastelandPlayer(player).setTeam(null);
    }

    public boolean contains(Player player){
        return member.contains(player);
    }

    public List<Player> getMember() {
        return member;
    }
}

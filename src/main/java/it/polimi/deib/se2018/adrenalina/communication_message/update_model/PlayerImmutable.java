package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public class PlayerImmutable implements Serializable
{
    private ColorId color;

    private int ammoYellow;

    private  int ammoRed;

    private  int ammoBlue;

    private String name;

    private String action_hero_comment;

    private ColorId[] damageCounter; //Represent the damages in player's board

    private int last; //Indicate the last position free in array of damage counter

    private List<ColorId> markCounter; //List that represent the mark in Player's Board

    private int deathsCounter;//Counter of the player's deaths

    private int score; //Score of the player

    private List<String> powerupCardList; //Array of the powerUps

    private List<String> weaponCardList; //Array of weapons

    public PlayerImmutable(Player player)
    {
        this.color = player.getColor();
        this.ammoYellow = player.getAmmoYellow();
        this.ammoRed = player.getAmmoRed();
        this.ammoBlue = player.getAmmoBlue();
        this.name = player.getName();
        this.action_hero_comment = player.getAction_hero_comment();
        this.damageCounter = player.getDamageCounter().clone();
        this.last = player.getNumberOfDamagePoint();
        this.markCounter = player.getMarkCounter();
        this.deathsCounter = player.getDeathsCounter();
        this.score = player.getScore();
        this.powerupCardList = player.getPowerupCardList()
                .stream().map(PowerUpCard::powerToString).collect(Collectors.toList());
        this.weaponCardList = player.getWeaponCardList()
                .stream().map(WeaponCard::getName).collect(Collectors.toList());
    }

    public ColorId getColor() {
        return color;
    }

    public int getAmmoYellow() {
        return ammoYellow;
    }

    public int getAmmoRed() {
        return ammoRed;
    }

    public int getAmmoBlue() {
        return ammoBlue;
    }

    public String getName() {
        return name;
    }

    public String getAction_hero_comment() {
        return action_hero_comment;
    }

    public ColorId[] getDamageCounter() {
        return damageCounter;
    }

    public int getLast() {
        return last;
    }

    public List<ColorId> getMarkCounter() {
        return markCounter;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public int getScore() {
        return score;
    }

    public List<String> getPowerupCardList() {
        return powerupCardList;
    }

    public List<String> getWeaponCardList() {
        return weaponCardList;
    }

    @Override
    public String toString() {
        return "color=" + color +
                "\n ammoYellow=" + ammoYellow +
                "\n ammoRed=" + ammoRed +
                "\n ammoBlue=" + ammoBlue +
                "\n name='" + name + '\'' +
                "\n Danni=" + last +
                "\n markCounter=" + markCounter +
                "\n deathsCounter=" + deathsCounter +
                "\n score=" + score +
                "\n powerupCardList=" + powerupCardList +
                "\n weaponCardList=" + weaponCardList
                ;
    }
}

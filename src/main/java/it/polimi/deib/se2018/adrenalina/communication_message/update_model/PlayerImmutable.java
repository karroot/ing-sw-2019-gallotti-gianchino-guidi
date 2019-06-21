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

    //###Location of the player(if x= 0 and y = 0 then the player isn't in a square)
    private int x;

    private int y;

    private  int ammoRed;

    private  int ammoBlue;

    private String name;

    private String action_hero_comment;

    private ColorId[] damageCounter; //Represent the damages in player's board

    private int last; //Indicate the last position free in array of damage counter

    private List<ColorId> markCounter; //List that represent the mark in Player's Board

    private int deathsCounter;//Counter of the player's deaths

    private int score; //Score of the player

    private List<PowerUpCardImmutable> powerupCardList; //Array of the powerUps

    private List<WeaponCardImmutable> weaponCardList; //Array of weapons

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
                .stream().map(PowerUpCard::getCopyImm).collect(Collectors.toList());
        this.weaponCardList = player.getWeaponCardList()
                .stream().map(WeaponCard::getCopyImm).collect(Collectors.toList());

        if (player.getSquare() == null) //If the player ins't square
        {
            x = 0;//Coordinates are zero
            y = 0;
        }
        else //Saves the coordinates of the square
        {
            x = player.getSquare().getX();
            y = player.getSquare().getY();
        }
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

    public List<PowerUpCardImmutable> getPowerupCardList() {
        return powerupCardList;
    }

    public List<WeaponCardImmutable> getWeaponCardList() {
        return weaponCardList;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString()
    {
        String location;

        if (x == 0 && y == 0)
            location = "Il PLAYER: " +name + "\nNon è in alcuno square";
        else
            location = "Il PLAYER: " +name +
                    "\n è nel quadrato con cordinate\n" +
                    "x = " + x +
                    "\ny = " + y + "\n";

        return "colore=" + color +
                "\n munizioni Gialle=" + ammoYellow +
                "\n munizioni Rosse =" + ammoRed +
                "\n munizioni Blu=" + ammoBlue +
                "\n nome ='" + name + '\'' +
                "\n Danni =" + last +
                "\n lista marchi =" + markCounter +
                "\n morti =" + deathsCounter +
                "\n punteggio =" + score +
                "\n PowerUp che hai=" + powerupCardList +
                "\n Armi che hai=" + weaponCardList +
                location
                ;
    }
}

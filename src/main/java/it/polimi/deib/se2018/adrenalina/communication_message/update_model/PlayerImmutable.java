package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represent a copy Immutable of a Player with all his information coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class PlayerImmutable implements Serializable
{
    private ColorId color;

    boolean flipped;

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

    /**
     * Create the copy immutable of the player
     * @param player object player from the model
     */
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

        this.flipped = player.isFlipped();

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

    /**
     * Getter for the player's color
     * @return player's color
     */
    public ColorId getColor() {
        return color;
    }

    /**
     * Getter for the number of Yellow Ammo
     * @return number of Yellow Ammo
     */
    public int getAmmoYellow() {
        return ammoYellow;
    }

    /**
     * Getter for the number of Red Ammo
     * @return number of Red Ammo
     */
    public int getAmmoRed() {
        return ammoRed;
    }

    /**
     * Getter for the number of Blue Ammo
     * @return number of Blue Ammo
     */
    public int getAmmoBlue() {
        return ammoBlue;
    }

    /**
     * Getter for the player's name
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the player's hero comment
     * @return player's hero comment
     */
    public String getAction_hero_comment() {
        return action_hero_comment;
    }

    /**
     * Getter for the player's damage counter
     * @return player's damage counter
     */
    public ColorId[] getDamageCounter() {
        return damageCounter;
    }

    /**
     * Getter for the number of damage Points
     * @return number of damage Points
     */
    public int getLast() {
        return last;
    }

    /**
     * Getter for all markers that the player has too
     * @return list of all markers
     */
    public List<ColorId> getMarkCounter() {
        return markCounter;
    }

    /**
     * Getter for the number of deaths
     * @return number of deaths
     */
    public int getDeathsCounter() {
        return deathsCounter;
    }

    /**
     * Getter for the player's score
     * @return player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for the all player's powerUp cards
     * @return list of all player's powerUp cards
     */
    public List<PowerUpCardImmutable> getPowerupCardList() {
        return powerupCardList;
    }

    /**
     * Getter for the all player's Weapons cards
     * @return list of all player's Weapons cards
     */
    public List<WeaponCardImmutable> getWeaponCardList() {
        return weaponCardList;
    }

    /**
     * Getter for coordinate x of the square where is located the player
     * @return coordinate x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the coordinate y of the square where is located the player
     * @return coordinate y
     */
    public int getY() {
        return y;
    }

    /**
     * Say if the player's board is flipped for the FRENZY mode
     * @return player's board is flipped or not
     */
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * This method will be used by Cli to print all the player's information
     * @return string to print
     */
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

package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerupCard;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.*;

public class Player
{


    private ColorId color;

    private int ammoYellow;

    private  int ammoRed;

    private  int ammoBlue;

    private String name;

    private String action_hero_comment;

    private boolean isFirst;

    private Color[] damageCounter;

    /**
     * It has to be a 5x3 matrix
     */
    private Color[] markCounter;

    private int deathsCounter;

    public  int score;

    private Square square;

    private List<PowerupCard> powerupCardList;

    private List<WeaponCard> weaponCardList;


    public Player()
    {

    }


    public void getWeapon()
    {

    }

    public void getPowerUp()
    {

    }

    public void usePowerUp()
    {

    }

}
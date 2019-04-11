package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerupCard;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.*;

public abstract class GameBoard {


    private Stack<WeaponCard> weaponCardStackeaponStack;

    private Stack<PowerupCard> powerupCardStack;

    private List<Room> roomList;

    private List<Square> squareList;


    //Rep
    protected Map killshotTrack;



    public GameBoard() {
    }

    /**
     We use a dictionary that has the player as key and as value
     another data structure that tells us which kill on the track did and if it did.*/

    //todo grafo






}
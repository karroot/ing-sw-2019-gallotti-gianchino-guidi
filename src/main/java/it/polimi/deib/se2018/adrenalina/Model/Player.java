package it.polimi.deib.se2018.adrenalina.Model;
import it.polimi.deib.se2018.adrenalina.Model.Death;

import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.*;

/**
 * @author Cysko7927,Gallotti, Guidi
 * It represents the player and performs the aviable actions that he can do
 */
public class Player
{


    private ColorId color;

    private int ammoYellow;

    private  int ammoRed;

    private  int ammoBlue;

    private String name;

    private String action_hero_comment;

    private boolean isFirst;

    //Rep of the damage counter
    //################

    private ColorId[] damageCounter; //Represent the damages in player's board

    private int last; //Indicate the last position free in array of damage counter

    //#################

    private StatePlayer state;

    private List<ColorId> markCounter; //List that represent the mark in Player's Board

    private int deathsCounter;//Counter of the player's deaths

    private int score; //Score of the player

    private boolean overKill;

    private Square square;

    private List<PowerUpCard> powerupCardList; //Array of the powerUps

    private List<WeaponCard> weaponCardList; //Array of weapons

    /**
     * instantiates the player's class for the start game
     *
     * @param color Color of the player
     * @param name name of the player
     * @param action_hero_comment comment of battle
     * @param isFirst Is the first player?
     */
    public Player(ColorId color, String name, String action_hero_comment, boolean isFirst)
    {
        this.color = color;
        this.name = name;
        this.action_hero_comment = action_hero_comment;
        ammoYellow = 1;
        ammoRed = 1;
        ammoBlue =1;
        last = 0;
        this.isFirst = isFirst;
        deathsCounter = 0;
        score = 0;
        powerupCardList = new ArrayList<>();
        weaponCardList = new ArrayList<>();
        damageCounter = new ColorId[12];
        markCounter = new ArrayList<>();
        square = null;
        state = new Normal(); //Player is in normal condition(No adrenalized effects)
        overKill = false;
    }


    //Getter
    //-----------------------------
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

    public boolean isFirst() {
        return isFirst;
    }

    public ColorId[] getDamageCounter() {
        return damageCounter;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public int getScore() {
        return score;
    }

    public Square getSquare() {
        return square;
    }

    public int getLast() {
        return last;
    }



    //Setter
    //------------------------

    public void setLast(int last) {
        this.last = last;
    }


    public void setAmmoYellow(int ammoYellow) {
        this.ammoYellow = ammoYellow;
    }

    public void setAmmoRed(int ammoRed) {
        this.ammoRed = ammoRed;
    }

    public void setAmmoBlue(int ammoBlue) {
        this.ammoBlue = ammoBlue;
    }

    public void setState(StatePlayer state) {
        this.state = state;
    }

    public void setDeathsCounter(int deathsCounter) {
        this.deathsCounter = deathsCounter;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSquare(Square square) {
        this.square = square;
    }



    //---------------------------

    /**
     * Add a weapon card in player's deck if weaponCardList.size minor 3
     * @param weapon weapon that the player grabs from game board
     *
     */
    public void getWeapon(WeaponCard weapon) //Deve lanciare eccezione full deck weapons
    {
        if (weaponCardList.size() < 3 && weaponCardList.size() >= 0)
            weaponCardList.add(weapon);

        else {
                //Lancia eccezione full deck weapon
             }

    }

    /**
     * Exchange a old weapon with a new from game board and it returns the old too
     * (requires) the old weapon must be in list of weapon card
     * @param newWeapon  new weapon card to add in player's deck
     * @param oldWeapon name of the weapon in player's deck to exchange with the new too
     * @return the old weapon that was in player's deck
     */
    public WeaponCard changeWeapon(WeaponCard newWeapon, String oldWeapon)
    {
        WeaponCard temp = null;

        for (WeaponCard x:weaponCardList) //Search in List of weapon card
        {
            if (x.getName().equals(oldWeapon)) //If you founded the weapon
            {
                temp = x; //Save the card
                weaponCardList.remove(x); //Remove old weapon from deck's player
            }


        }

        weaponCardList.add(newWeapon);//Add the new weapon in player's deck

        return temp; //Return  old weapon
    }

    /**
     * Add a power up card in player's deck
     * @param powerupCard power up card to add
     */
    public void getPowerUp(PowerUpCard powerupCard)
    {
        powerupCardList.add(powerupCard);
    }

    /**
     * Remove card power up from player's deck that has been used
     * @param indexDeckCard index of card power up that has been used
     * @return card power up that has been used
     */
    public PowerUpCard usePowerUp(int indexDeckCard)
    {
        PowerUpCard temp = powerupCardList.get(indexDeckCard);

        powerupCardList.remove(indexDeckCard);

        return temp;

    }

    //Procedures to handle the marks


    /**
     * Add mark done by a player if checkMarker(player) is between 0 and 2 else it does not add
     * @param player color of the player that marked this
     */
    public void addMark(ColorId player)
    {
        if (checkMarker(player) < 3 && checkMarker(player) >= 0) //If the player didn't do 3 marks
            markCounter.add(player);//Add marks on player's board
    }

    /**
     * Remove a mark and use it like damage if damage counter is not full else mark is lost
     * @param player player to witch belong the mark
     */
    public void useMark(ColorId player)
    {
        markCounter.remove(player); //Remove marker from player's board

        if (last >= 0 && last <= 11)
        {
            damageCounter[last] = player;//Add a damage point in player's board
            last++; //next place in array is free
        }

        checkState(); //Check the state of the player
    }

    /**
     * //Calculate the number of marks done by a player
     * @param player player that done the marks
     * @return number of marks done by player
     */
    public int checkMarker(ColorId player)
    {
        int cont = 0; //Reset counter

        for (ColorId x:markCounter) //search in list of marks
        {
            if (x.equals(player)) //If marks x is of the player
                cont++;//Increment counter
        }

        return cont;//Return counter
    }

    /**
     * Add a damage point in player's deck if there aren't 12 point damage
     * @param player player that did the damage
     */
    public void doDamage(ColorId player)
    {
        if (last >=0 && last <=11) //If there aren't 12 point damage
        {
            damageCounter[last] = player; //Do point damage
            last++;
        }

        checkState();//Check the state of player
    }

    //Operations to handle score

    /**
     * if damage of this player is 11 or 12 returns a hash map with key color of player and value the score that he obtained doing damage
     * to this player and it free player's board
     * Else it does nothing
     * @return Score
     */
    public Map<ColorId,Integer> calculateScore()
    {

        return new HashMap<>();
    };

    //#################################
    //Private methods


    //Handle the state of the player(implements a transition function)
    private void checkState()
    {
        if(last >= 0 && last <=2) //If the damage points are between 0 and 2
        {
            state = new Normal();//player is in normal condition
            this.overKill = false;
        }
        if (last >= 3 && last <= 5) //If the damage points are between 3 and 5
            state = new Adrenalized1(); //player has an adrenaline action

        if (last >= 6 && last <=10)//If the damage points are between 6 and 10
            state = new Adrenalized2(); //player has the two adrenaline action

        if(last == 11) //If the damage points are 11
            state = new Death(); //player is death

        if(last == 12) //If the damage points are 12
             this.overKill = true; //player is overKilled
    }

}
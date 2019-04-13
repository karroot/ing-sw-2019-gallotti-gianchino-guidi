package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;
import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.*;

public abstract class GameBoard {


    private Stack<WeaponCard> weaponCardStack;



    private Stack<PowerUpCard> powerUpCardStack;

    private List<Room> roomList;

    private Graph arena; // remember to change in UML

    /**
     * Create the GameBoard with its parameters
     * @param weaponCardStack Stack of Weapon cards
     * @param powerUpCardStack Stack of PowerUp cards
     * @param roomList list of room
     * @param arena Map of the arena
     * @param killshotTrack
     */
    public GameBoard(Stack<WeaponCard> weaponCardStack, Stack<PowerUpCard> powerUpCardStack, List<Room> roomList, Graph arena, Map killshotTrack) {
        this.weaponCardStack = weaponCardStack;
        this.powerUpCardStack = powerUpCardStack;
        this.roomList = roomList;
        this.arena = arena;
        this.killshotTrack = killshotTrack; // structure must change
    }

    //Rep
    protected Map killshotTrack;



    //Getter
    //-----------------------------
    public List<Room> getRoomList()
    {
        return roomList;
    }

    public Graph getArena()
    {
        return arena;
    }

    /**
     *It Return the first card of the weaponCard deck and remove it from the deck
     * @return
     */
    public WeaponCard getWeaponCard() {
        if (!weaponCardStack.isEmpty()) {
            WeaponCard pop = weaponCardStack.pop();
            return pop;
        }
        else {
            return null;
        }
    }
    /**
     *It Return the first card of the powerUpCard deck and remove it from the deck
     * @return
     */
    public PowerUpCard getPowerUpCard() {
        if (!powerUpCardStack.isEmpty()) {
            PowerUpCard pop = powerUpCardStack.pop();
            return pop;
        }
        else {
            return null;
        }
    }

    //Setter
    //-----------------------------

    /**
     * It set the PowerUp card stack when it gets empty
     * @param powerUpCardStack The new deck that fill the stack
     */
    public void setPowerUpCardStack(Stack<PowerUpCard> powerUpCardStack)
    {
        this.powerUpCardStack = powerUpCardStack;
    }





    //---------------------------


/**
     We use a dictionary that has the player as key and as value
     another data structure that tells us which kill on the track did and if it did.*/

    //todo grafo






}
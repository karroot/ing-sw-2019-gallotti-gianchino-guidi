package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;
import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.*;

/**
 * @author Gallotti
 * It represents the structure of Track
 */
class Track
{
    private ColorId player;
    private int  pointCounter;
    public Track(ColorId player,int pointCounter){
        this.player=player;
        this.pointCounter=pointCounter;

    }

    public ColorId getPlayer() {
        return player;
    }

    public int getPointCounter() {
        return pointCounter;
    }
};
/**
 * @author Gallotti
 * It represents the gameboard and performs the aviable actions that he can do on the elements of gameboard
 */
public abstract class GameBoard {



    private Stack<WeaponCard> weaponCardStack;

    private Stack<AmmoTiles> ammoTilesStack;

    private Stack<PowerUpCard> powerUpCardStack;

    private List<Room> roomList;

    private static Graph arena; // remember to change in UML

    private int skullCounter;

    private List<Track> killShotTrack;



    /**
     * Create the GameBoard with its parameters
     * @param weaponCardStack Stack of Weapon cards
     * @param powerUpCardStack Stack of PowerUp cards
     * @param roomList list of room
     * @param arena Map of the arena

     * @param skullCounter counter of the skull on board
     * @param ammoTilesStack Stack of ammo Cards
     */
    public GameBoard(Stack<WeaponCard> weaponCardStack, Stack<PowerUpCard> powerUpCardStack, List<Room> roomList, Graph arena, int skullCounter,Stack<AmmoTiles> ammoTilesStack) {
        this.weaponCardStack = weaponCardStack;
        this.powerUpCardStack = powerUpCardStack;
        this.roomList = roomList;
        this.arena = arena;
        this.skullCounter=skullCounter;
        this.ammoTilesStack=ammoTilesStack;

    }





    //Getter
    //-----------------------------
    public List<Room> getRoomList()
    {
        return roomList;
    }

    public static Graph getArena()
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


    public Stack<AmmoTiles> getAmmoTilesStack() {
        return ammoTilesStack;
    }

    public int getSkullCounter() {
        return skullCounter;
    }

    public Track getKillShotTrack(int index) {
        return killShotTrack.get(index);
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

    /**
     * it reduce the counter of skull when a player die
     * @param skullCounter
     */
    public void setSkullCounter(int skullCounter) {
        this.skullCounter = skullCounter;
    }

    /**
     * it add some damagePoint to killShotTrack
     * @param player define the player that had made the kill
     * @param counter define how many point you should add
     */
    public void setKillShotTrack( ColorId player, int counter) {
        this.killShotTrack.add(new Track(player,counter));
    }

//---------------------------


/**
     We use a dictionary that has the player as key and as value
     another data structure that tells us which kill on the track did and if it did.*/

    //todo grafo






}
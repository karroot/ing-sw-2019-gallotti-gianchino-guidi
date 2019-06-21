package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;

import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.*;


/**
 * @author Gallotti
 * It represents the gameboard and performs the aviable actions that he can do on the elements of gameboard
 */
public class GameBoard {


    private List<Player> allPlayerList; //list of all player

    private Stack<WeaponCard> weaponCardStack;

    private Stack<AmmoTiles> ammoTilesStack;

    private Stack<PowerUpCard> powerUpCardStack;

    private List<Room> roomList;

    private Graph arena;

    private int originalSkullCounter;

    private int skullCounter;

    private List<Track> killShotTrack;

    private int code;

    /**
     * Create the GameBoard with its parameters
     * @param weaponCardStack Stack of Weapon cards
     * @param powerUpCardStack Stack of PowerUp cards
     * @param codeArena code that identifies the arena
     * @param skullCounter counter of the skull on board
     * @param ammoTilesStack Stack of ammo Cards
     */
    public GameBoard(Stack<WeaponCard> weaponCardStack, Stack<PowerUpCard> powerUpCardStack, int codeArena , int skullCounter,Stack<AmmoTiles> ammoTilesStack) {
        this.weaponCardStack = weaponCardStack;
        this.powerUpCardStack = powerUpCardStack;
        this.arena = FactoryArena.getArena(codeArena,this);
        this.roomList = FactoryArena.getRooms(this);
        this.skullCounter=skullCounter;
        originalSkullCounter = skullCounter;
        this.ammoTilesStack=ammoTilesStack;
        this.killShotTrack= new LinkedList<>();
        this.allPlayerList = new ArrayList<>();
        this.code = codeArena;
    }





    //Getter
    //-----------------------------
    public List<Room> getRoomList()
    {
        return new ArrayList<>(roomList);
    }

    public int getCode() {
        return code;
    }

    public  Graph getArena()
    {
        return arena;
    }

    public int getOriginalSkullCounter() {
        return originalSkullCounter;
    }

    public List<Player> getAllPlayer()
    {
        return new LinkedList<>(allPlayerList);
    }

    /**
     *It Return the first card of the weaponCard deck and remove it from the deck
     * @return
     */
    public WeaponCard drawWeaponCard() throws NullPointerException {
        if (!weaponCardStack.isEmpty()) {
            WeaponCard pop = weaponCardStack.pop();
            return pop;
        }
        else {
            throw new NullPointerException("deck is empty");
        }
    }

    public Stack<WeaponCard> getWeaponCardStack() {
        return weaponCardStack;
    }

    public Stack<PowerUpCard> getPowerUpCardStack() {
        return powerUpCardStack;
    }

    /**
     *It Return the first card of the powerUpCard deck and remove it from the deck
     * @return
     */
    public PowerUpCard drawPowerUpCard() throws NullPointerException {
        if (!powerUpCardStack.isEmpty()) {
            PowerUpCard pop = powerUpCardStack.pop();
            return pop;
        }
        else {
            throw new NullPointerException("deck is empty");
        }
    }


    public Stack<AmmoTiles> getAmmoTilesStack() {
        return ammoTilesStack;
    }

    public int getSkullCounter() {
        return skullCounter;
    }

    public Track getKillShotTrack(int index)
    {
        return killShotTrack.get(index);
    }

    public ColorId getKillShotPlayer(Track kill) {
        return kill.getPlayer();
    }

    public int getKillShotPointCounter(Track kill) {
        return kill.getPointCounter();
    }

    public List<Track> getKillShotTrack()
    {
        return new ArrayList<>(killShotTrack);
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
     * //todo
     * @param ammoTilesStack
     */
    public void setAmmoTilesStack(Stack<AmmoTiles> ammoTilesStack)
    {
        this.ammoTilesStack = ammoTilesStack;
    }

    public void setAllPlayer(Player playerToAdd) {

        allPlayerList.add(playerToAdd);
    }

    /**
     * it reduce the counter of skull when a player die
     * @param skullCounter
     */

    public void setSkullCounter(int skullCounter) throws IllegalArgumentException{
        if(skullCounter<0){
            throw new IllegalArgumentException("negative value of skullCounter is not accepted");
        }
        else this.skullCounter = skullCounter;
    }
    /**
     * It set the weapon card stack when it gets empty
     * @param weaponCardStack The new deck that fill the stack
     */
    public void setWeaponCardStack(Stack<WeaponCard> weaponCardStack) {
        this.weaponCardStack = weaponCardStack;
    }

    /**
     * it add some damagePoint to killShotTrack
     * @param player define the player that had made the kill
     * @param counter define how many point you should add
     */
    public void setKillShotTrack( ColorId player, int counter) throws  IllegalArgumentException
    {
        if (counter < 1)
            throw new IllegalArgumentException("less than 0 value of killshottrack is not accepted");

        this.killShotTrack.add(new Track(player,counter));
    }

//---------------------------








}
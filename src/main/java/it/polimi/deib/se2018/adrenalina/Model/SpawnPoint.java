package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.LinkedList;
import java.util.List;

/**
 * @author giovanni
 *
 * This class implements a point where the player can spawn from
 */

public class SpawnPoint extends Square
{
    private List<WeaponCard> weaponCardList;

    /**
     * This is the public constructor of this class.
     *
     * @param x is the x coordinate of the square
     * @param y is the y coordinate of the square
     * @param gameBoard is the refence to the gameboard
     * @param color is the color of the room
     * @param side is the array that defines the side types of this square
     */
    public SpawnPoint(int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        super(x, y, gameBoard, color, side);
        isSpawnPoint = true;
        isAmmoPoint = false;
        weaponCardList = new LinkedList<>();
    }


    /**
     * This is the public setter for the weaponCardList attribute
     *
     * @param weaponCardList is the list that will be set
     */
    public void setWeaponCardList(List<WeaponCard> weaponCardList) {
        this.weaponCardList = weaponCardList;
    }

    /**
     * This is the public getter for the WeaponCardList
     *
     * @return the list of weapons
     */
    public List<WeaponCard> getWeaponCardList()
    {
        return weaponCardList;
    }


    /**
     * It removes the weapon from the weaponCardList and it returns it
     *
     * @param chosenWeapon is the weapon to be removed from the point
     * @return the weapon we have just drawn
     */
    public WeaponCard drawWeapon (WeaponCard chosenWeapon)
    {
        weaponCardList.remove(chosenWeapon);
        return chosenWeapon;
    }


    /**
     * Swap the selected weapon the player has with a new one.
     *
     * @param chosenNewWeapon is the weapon we will get
     * @param chosenOldWeapon is the weapon we will drop on the board
     * @return the weapon we will get
     */
    public WeaponCard swapWeapon (WeaponCard chosenNewWeapon, WeaponCard chosenOldWeapon)
    {
        weaponCardList.remove(chosenNewWeapon);
        chosenOldWeapon.setLoaded(true);
        weaponCardList.add(chosenOldWeapon);
        return chosenNewWeapon;
    }


    /**
     * This method pops a new weapon from the gameboard weapon stack and add it to the list of this spawn
     *
     */
    public void addNewWeapon ()
    {
        weaponCardList.add(getGameBoard().drawWeaponCard());
    }
}
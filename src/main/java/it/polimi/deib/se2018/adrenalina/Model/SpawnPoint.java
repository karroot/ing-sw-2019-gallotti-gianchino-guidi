package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.List;

/**
 * @author giovanni
 * This class implements a point where the player can spawn from.
 */

public class SpawnPoint extends Square
{

    /*
     * @attribute weaponCardList is the 3 weapon list to choose from
     *
     */

    private List<WeaponCard> weaponCardList;

    /**
     * This is the public constructor for the class
     *
     * @param x
     * @param y
     * @param gameBoard
     * @param color
     * @param side
     */
    public SpawnPoint(int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        super(x, y, gameBoard, color, side);
    }

    /**
     * This is the getter for the WeaponCardList
     * @return the list of weapons
     */
    public List<WeaponCard> getWeaponCardList()
    {
        return weaponCardList;
    }


    /**
     * It removes the chosenweapon from the weaponCardList and it returns it
     *
     * @param chosenWeapon is the weapon we will get
     * @return the weapon we have just drawn
     */
    public WeaponCard drawWeapon (WeaponCard chosenWeapon)
    {
        weaponCardList.remove(chosenWeapon);
        return chosenWeapon;
    }


    /**
     * Swap the selected weapon the player has with a new one.
     * It returns the new weapon chosen.
     * @param chosenNewWeapon is the weapon we will get
     * @param chosenOldWeapon is the weapon we will drop on the board
     * @return the weapon we will get
     */
    public WeaponCard swapWeapon (WeaponCard chosenNewWeapon, WeaponCard chosenOldWeapon)
    {
        weaponCardList.remove(chosenNewWeapon);
        weaponCardList.add(chosenOldWeapon);
        return chosenNewWeapon;
    }


    /**
     * This method pops a new weapon from the gameboard weapon stack and add it to the list of this spawn
     */
    public void addNewWeapon ()
    {
        GameBoard tempGame = getGameBoard();
        weaponCardList.add(tempGame.drawWeaponCard());
    }
}
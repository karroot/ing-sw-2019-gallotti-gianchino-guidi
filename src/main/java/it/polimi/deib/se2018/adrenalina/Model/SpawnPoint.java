package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.List;

public class SpawnPoint extends Square
{
    /*
    This class implements a point where the player can spawn from.
    This point has 3 possible weapon to choose from.
     */


    private List<WeaponCard> weaponCardList;

    /*
    Public constructor
     */
    public SpawnPoint(int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        super(x, y, gameBoard, color, side);
    }

    /*
    Getter of weaponCardList
     */
    public List<WeaponCard> getWeaponCardList()
    {
        return weaponCardList;
    }

    /*
    It removes the chosenweapon from the eeaponCardList and it returns it
     */
    public WeaponCard getWeapon (WeaponCard chosenWeapon)
    {
        weaponCardList.remove(chosenWeapon);
        return chosenWeapon;
    }

    /*
    Swap the selected weapon the player has with a new one.
    It returns the new weapon chosen.
     */
    public WeaponCard swapWeapon (WeaponCard chosenNewWeapon, WeaponCard chosenOldWeapon)
    {
        weaponCardList.remove(chosenNewWeapon);
        weaponCardList.add(chosenOldWeapon);
        return chosenNewWeapon;
    }


    /*
    pops a new weapon from the gameboard weapon stack and add it to the list of this spawn
     */
    public void addNewWeapon ()
    {
        GameBoard tempGame = getGameBoard();
        weaponCardList.add(tempGame.getWeaponCard());
    }
}
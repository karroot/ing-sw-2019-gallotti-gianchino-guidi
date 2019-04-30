import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is a case test for the AmmoPoint.
 *
 * @author giovanni
 */


public class TestAmmoPoint {

    SpawnPoint spawnPointTest;
    AmmoPoint ammoPointTest;


    @Before



    @Test
    //crea spanwpoint
    public void setupSpawn ()
    {
        new SpawnPoint()
    }

    @Test
    //crea ammo

    public void setupAmmo ()
    {
        new AmmoPoint()
    }

    @Test
    /*
    Questi metodi:
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

    public void drawWeaponTest ()
    {

    }

    @Test

    public void swapWeaponTest () {

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

    @Test
    public void addNewWeaponTest () {

    }
    /**
     * This method pops a new weapon from the gameboard weapon stack and add it to the list of this spawn
     */
    public void addNewWeapon ()
    {
        GameBoard tempGame = getGameBoard();
        weaponCardList.add(tempGame.getWeaponCard());
    }

    //questi metodi
    @Test
    public void useAmmoTilesTest () {

    }
    /**
     * This method draw the AmmoTiles card that is on the point, and it sets the ammoTiles of the point to null
     *
     * @param player is who will get the ammo and the powerup if present
     * @return the AmmoTiles that is used
     */
    public AmmoTiles useAmmoTiles (Player player)
    {
        AmmoTiles tempAmmoTiles = ammoTiles;
        ammoTiles.useAmmoTilesCards(player);
        ammoTiles = null;
        return tempAmmoTiles;
    }
    @Test
    public void replaceAmmoTilesTest () {

    }
    /**
     * This method replaces the ammoTiles card on this square.
     * It supposes a Player called the getAmmoTile before.
     */
    public void replaceAmmoTiles ()
    {
        GameBoard tempGame = getGameBoard();
        ammoTiles = tempGame.getAmmoTilesStack().pop();
    }

}

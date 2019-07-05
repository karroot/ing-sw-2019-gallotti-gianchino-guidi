package it.polimi.deib.se2018.adrenalina.Model;

/**
 * This interface will be extended by the classes JustAmmo and PowerAndAmmo.
 *
 * @author gioguidi
 */

public interface AmmoTiles
{
    /**
     * This public method that uses the ammoTile card. It will be overrided by the classes JustAmmo and PowerAndAmmo.
     *
     * @param player is the player that will use the AmmoTile card
     */
    public void useAmmoTilesCards (Player player);

}
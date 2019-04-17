package it.polimi.deib.se2018.adrenalina.Model;

import java.util.*;

/**
 * This interfale will be extended by the classes JustAmmo and PowerAndAmmo.
 *
 * @author giovanni
 */

public interface AmmoTiles
{
    /**
     * This public method that uses the ammoTile card will be overrided by the classes JustAmmo and PowerAndAmmo.
     *
     * @param player is the player that will use the AmmoTile card
     */
    public void useAmmoTilesCards (Player player);

}
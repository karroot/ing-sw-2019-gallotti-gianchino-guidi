package it.polimi.deib.se2018.adrenalina.Model;

import java.util.Collections;
import java.util.Objects;

/**
 * This class implements a Square where there is an AmmoTile.
 * @author giovanni
 */
public class AmmoPoint extends Square
{
    /*
     * @attribute ammotiles is the  AmmoTile to grab.
     */
    private AmmoTiles ammoTiles;


    /**
     * This method is a public getter for the ammoTiles.
     *
     * @return the ammoTiles card
     */
    public AmmoTiles getAmmoTiles()
    {
        return ammoTiles;
    }



    /**
     * This method draws and uses the AmmoTiles card that is on the point, and it sets the ammoTiles of the point to null
     *
     * @param player is who will get the ammo and the powerup if present
     * @return the AmmoTiles that is used
     */
    public void useAmmoTiles (Player player)
    {
        player.getSquare().getGameBoard().getAmmoTilesDiscardStack().addElement(ammoTiles);
        ammoTiles.useAmmoTilesCards(player);
        ammoTiles = null;
    }

    /**
     * This method replaces the ammoTiles card on this square.
     * It supposes a Player called the getAmmoTile before.
     */
    public void replaceAmmoTiles ()
    {
        if (getGameBoard().getAmmoTilesStack().isEmpty())
            for (AmmoTiles ammoTilesIterate : getGameBoard().getAmmoTilesDiscardStack())
            {
                getGameBoard().getAmmoTilesStack().addElement(ammoTilesIterate);
                getGameBoard().getAmmoTilesDiscardStack().remove(ammoTilesIterate);

                Collections.shuffle(getGameBoard().getAmmoTilesStack());
            }

        ammoTiles = getGameBoard().getAmmoTilesStack().pop();

    }

    /**
     * /todo
     * @param ammoTiles
     */
    public void setAmmoTiles (AmmoTiles ammoTiles)
    {
        this.ammoTiles=ammoTiles;
    }

    /**
     *
     * @param x
     */
    public void setX (int x)
    {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY (int y)
    {
        this.y = y;
    }





    /**
     * This is the public constructor of this class.
     *
     * @param x is the x coordinate of the square
     * @param y is the y coordinate of the square
     * @param gameBoard is the refence to the gameboard
     * @param color is the color of the room
     * @param side is the array that defines the side types of this square
     */
    public AmmoPoint (int x, int y, GameBoard gameBoard, ColorRoom color, SideType[] side)
    {
        super(x,y,gameBoard,color,side);
        isAmmoPoint = true;
        isSpawnPoint = false;
    }


}

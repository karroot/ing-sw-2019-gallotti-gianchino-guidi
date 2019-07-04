package it.polimi.deib.se2018.adrenalina.Model;

import java.util.Collections;
import java.util.Objects;

/**
 * This class implements an ammopoint: a square where there is an AmmoTile to grab.
 *
 * @author giovanni
 */
public class AmmoPoint extends Square
{
    private AmmoTiles ammoTiles;


    /**
     * This method is a public getter for the ammoTiles attribute.
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
     * @param player is who will use the ammotiles card
     */
    public void useAmmoTiles (Player player)
    {
        player.getSquare().getGameBoard().getAmmoTilesDiscardStack().addElement(ammoTiles);
        ammoTiles.useAmmoTilesCards(player);
        ammoTiles = null;
    }

    /**
     * This method replaces the ammoTiles card on this square.
     * If the ammotiles stack if empty, it will also recreate the stack from the discard ammotiles stack.
     */
    public void replaceAmmoTiles ()
    {
        if (getGameBoard().getAmmoTilesStack().isEmpty()) {
            for (AmmoTiles ammoTilesIterate : getGameBoard().getAmmoTilesDiscardStack())
            {
                getGameBoard().getAmmoTilesStack().addElement(ammoTilesIterate);
            }

            Collections.shuffle(getGameBoard().getAmmoTilesStack());
            getGameBoard().getAmmoTilesDiscardStack().clear();
        }

        ammoTiles = getGameBoard().getAmmoTilesStack().pop();

    }

    /**
     * This is a public setter for the attribute ammotile.
     *
     * @param ammoTiles is the ammotiles that will be set.
     */
    public void setAmmoTiles (AmmoTiles ammoTiles)
    {
        this.ammoTiles=ammoTiles;
    }

    /**
     * This is a public setter for the x coordinate of the class.
     *
     * @param x is the x coordinate to set
     */
    public void setX (int x)
    {
        this.x = x;
    }

    /**
     * This is a public setter for the x coordinate of the class.
     *
     * @param y is the y coordinate to set
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

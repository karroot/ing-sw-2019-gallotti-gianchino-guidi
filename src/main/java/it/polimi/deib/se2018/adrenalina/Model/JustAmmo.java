package it.polimi.deib.se2018.adrenalina.Model;


/**
 * This class implements the AmmoTile card that provides 3 ammo.
 *
 * @author gioguidi
 */

public class JustAmmo implements AmmoTiles
{

    private int ammoCardID;
    private Color singleAmmo;
    private Color doubleAmmo;

    /**
     * Create a card with its parameters
     *
     * @param ammoCardID is the unique int that identifies a single card. We will use the same ID that are in the file containings the images of the cards
     * @param singleAmmo indicates the first ammo drawn
     * @param doubleAmmo indicates the color of the 2 same colored ammo drawn
     */
    public JustAmmo(int ammoCardID, Color singleAmmo, Color doubleAmmo)
    {
        this.ammoCardID=ammoCardID;
        this.singleAmmo=singleAmmo;
        this.doubleAmmo=doubleAmmo;
    }

    /**
     * It will add the ammos and the powerup card to the player.
     *
     * @param player is the player that will use the AmmoTile card
     */
    @Override
    public void useAmmoTilesCards(Player player) {

        // The logic behind the method is the following: I check in the order RED, YELLOW, BLUE if the color matches the attribute of the class.
        // If it matches I will ++ the same color attribute the the player ammostack.

        if (singleAmmo == Color.RED)
        {
                player.setAmmoRed(player.getAmmoRed()+1);
        }
        else if (singleAmmo == Color.YELLOW)
        {
                player.setAmmoYellow(player.getAmmoYellow()+1);
        }
        else if (singleAmmo == Color.BLUE)
        {
                player.setAmmoBlue(player.getAmmoBlue()+1);
        }

        // I repeat the same logic for the second pair of ammo of the class.

        if (doubleAmmo == Color.RED)
        {
                player.setAmmoRed(player.getAmmoRed()+2);
        }
        else if (doubleAmmo == Color.YELLOW)
        {
                player.setAmmoYellow(player.getAmmoYellow()+2);
        }
        else if (doubleAmmo == Color.BLUE)
        {
                player.setAmmoBlue(player.getAmmoBlue()+2);
        }
    }

    /**
     * This is the public getter for the attribute ammoCardID
     *
     * @return the ammoCardID attribute
     */
    public int getAmmoCardID()
    {
        return ammoCardID;
    }

    /**
     * This is the public getter for the attribute singleAmmo
     *
     * @return the singleAmmo attribute
     */
    public Color getSingleAmmo()
    {
        return singleAmmo;
    }

    /**
     * This is the public getter for the attribute doubleAmmo
     *
     * @return the doubleAmmo attribute
     */
    public Color getDoubleAmmo()
    {
        return doubleAmmo;
    }

}
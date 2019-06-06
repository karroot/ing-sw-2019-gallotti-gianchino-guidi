package it.polimi.deib.se2018.adrenalina.Model;

import java.util.Set;

public class JustAmmo implements AmmoTiles
{
// nella funzione che genera gameboard chiami costruttore 36 volte con id diverso

    private int ammoCardID;
    private Color singleAmmo;
    private Color doubleAmmo;

    /**
     * Create a card with its parameters
     * @param ammoCardID is the unique int that identifies a single card. We will use the same ID that are in the file containings the images of the cards
     * @param singleAmmo indicates the first ammo drawn
     * @param doubleAmmo indicates the color of the 2 same ammo drawn
     */
    public JustAmmo(int ammoCardID, Color singleAmmo, Color doubleAmmo)
    {
        this.ammoCardID=ammoCardID;
        this.singleAmmo=singleAmmo;
        this.doubleAmmo=doubleAmmo;


    }

    /**
     * This method will be implemented in the classes JustAmmo and PowerAndAmmo.
     * It will add the ammos and the powerup card to the player.
     * @param player is the player that will use the AmmoTile card
     */
    @Override
    public void useAmmoTilesCards(Player player) {

        // The logic behind the method is the following: I check in the order RED, YELLOW, BLUE if the color matches the attribute of the class.
        // If it matches I will ++ the same color attribute the the player ammostack.

        if (singleAmmo == Color.RED)
        {
            if(player.getAmmoRed()<3)
                player.setAmmoRed(player.getAmmoRed()+1);
        }
        else if (singleAmmo == Color.YELLOW)
        {
            if(player.getAmmoYellow()<3)
                player.setAmmoYellow(player.getAmmoYellow()+1);
        }
        else if (singleAmmo == Color.BLUE)
        {
            if(player.getAmmoBlue()<3)
                player.setAmmoBlue(player.getAmmoBlue()+1);
        }

        // I repeat the same logic for the second pair of ammo of the class.

        if (doubleAmmo == Color.RED)
        {
            if(player.getAmmoRed()<2)
                player.setAmmoRed(player.getAmmoRed()+2);
        }
        else if (doubleAmmo == Color.YELLOW)
        {
            if(player.getAmmoYellow()<2)
                player.setAmmoYellow(player.getAmmoYellow()+2);
        }
        else if (doubleAmmo == Color.BLUE)
        {
            if(player.getAmmoBlue()<2)
                player.setAmmoBlue(player.getAmmoBlue()+2);
        }
    }

    public int getAmmoCardID() {
        return ammoCardID;
    }

    public Color getSingleAmmo() {
        return singleAmmo;
    }

    public Color getDoubleAmmo() {
        return doubleAmmo;
    }

    /*
        new JustAmmo = JustAmmo (042, YELLOW, BLUE);
        new JustAmmo = JustAmmo (043, YELLOW, RED);
        new JustAmmo = JustAmmo (044, RED, BLUE);
        new JustAmmo = JustAmmo (045, RED, YELLOW);
        new JustAmmo = JustAmmo (046, BLUE, YELLOW);
        new JustAmmo = JustAmmo (047, BLUE, RED);
        new JustAmmo = JustAmmo (048, YELLOW, BLUE);
        new JustAmmo = JustAmmo (049, YELLOW, RED);
        new JustAmmo = JustAmmo (0410, RED, BLUE);
        new JustAmmo = JustAmmo (0411, RED, YELLOW);
        new JustAmmo = JustAmmo (0412, BLUE, YELLOW);
        new JustAmmo = JustAmmo (0413, BLUE, RED);
        new JustAmmo = JustAmmo (0414, YELLOW, BLUE);
        new JustAmmo = JustAmmo (0415, YELLOW, RED);
        new JustAmmo = JustAmmo (0416, RED, BLUE);
        new JustAmmo = JustAmmo (0417, RED, YELLOW);
        new JustAmmo = JustAmmo (0418, BLUE, YELLOW);
        new JustAmmo = JustAmmo (0419, BLUE, RED);

         */
}
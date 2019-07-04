package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;

/**
 * This class implements the AmmoTile card that provides 2 ammo and a powerUP card.
 *
 * @author giovanni
 */

public class PowerAndAmmo implements AmmoTiles
{
    private int ammoCardID;
    private Color singleAmmo;
    private Color secondSingleAmmo;

    /**
     * Create a card with its parameters
     *
     * @param ammoCardID is the unique int that identifies a single card. We will use the same ID that are in the file containings the images of the cards
     * @param singleAmmo indicates the first ammo drawn
     * @param secondSingleAmmo indicates the second ammo drawn
     *
     */
    public PowerAndAmmo(int ammoCardID, Color singleAmmo, Color secondSingleAmmo)
    {
        this.ammoCardID=ammoCardID;
        this.singleAmmo=singleAmmo;
        this.secondSingleAmmo=secondSingleAmmo;

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

        // I repeat the same logic for the second ammo of the class.

        if (secondSingleAmmo == Color.RED)
        {
                player.setAmmoRed(player.getAmmoRed()+1);
        }
        else if (secondSingleAmmo == Color.YELLOW)
        {
                player.setAmmoYellow(player.getAmmoYellow()+1);
        }
        else if (secondSingleAmmo == Color.BLUE)
        {
                player.setAmmoBlue(player.getAmmoBlue()+1);
        }

        if (player.getPowerupCardList().size() < 3)
        {
            PowerUpCard pc= player.getSquare().getGameBoard().drawPowerUpCard();
            player.addPowerUp(pc);
            pc.setPlayer(player);
        }
    }

    /**
     * This is the public getter for the attribute ammoCardID
     * @return the ammoCardID attribute
     */
    public int getAmmoCardID() {
        return ammoCardID;
    }

    /**
     * This is the public getter for the attribute singleAmmo
     * @return the singleAmmo attribute
     */
    public Color getSingleAmmo() {
        return singleAmmo;
    }

    /**
     * This is the public getter for the attribute secondSingleAmmo
     * @return the secondSingleAmmo attribute
     */
    public Color getSecondSingleAmmo() {
        return secondSingleAmmo;
    }

}
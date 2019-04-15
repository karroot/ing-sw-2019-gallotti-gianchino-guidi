package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerUpCard;

public class PowerAndAmmo extends AmmoTiles
{
    /*
    This class implements the AmmoTile card that provides 2 ammo and a powerUP card.
    Todo: when we use this we must remeber to call the drawPowerUp action
     */
    private int ammoCardID;
    private Color singleAmmo;
    private Color secondSingleAmmo;
    //private PowerUpCard powerUpCard; NB we do not use this but we must drew a powerup card when we drew this card


    /**
     * Create a card with its parameters
     * @param ammoCardID is the unique int that identifies a single card. We will use the same ID that are in the file containings the images of the cards
     * @param singleAmmo indicates the first ammo drawn
     * @param secondSingleAmmo indicates the second ammo drawn
     */
    public PowerAndAmmo(int ammoCardID, Color singleAmmo, Color secondSingleAmmo, PowerUpCard powerUpCard)
    {
        this.ammoCardID=ammoCardID;
        this.singleAmmo=singleAmmo;
        this.secondSingleAmmo=secondSingleAmmo;

    }


    /*
        new JustAmmo = JustAmmo (0420, YELLOW, YELLOW);
        new JustAmmo = JustAmmo (0421, RED, RED);
        new JustAmmo = JustAmmo (0422, BLUE, BLUE);
        new JustAmmo = JustAmmo (0423, YELLOW, RED);
        new JustAmmo = JustAmmo (0424, YELLOW, BLUE);
        new JustAmmo = JustAmmo (0425, RED, BLUE);
        new JustAmmo = JustAmmo (0426, YELLOW, RED);
        new JustAmmo = JustAmmo (0427, YELLOW, BLUE);
        new JustAmmo = JustAmmo (0428, RED, BLUE);
        new JustAmmo = JustAmmo (0429, YELLOW, YELLOW);
        new JustAmmo = JustAmmo (0430, RED, RED);
        new JustAmmo = JustAmmo (0431, BLUE, BLUE);
        new JustAmmo = JustAmmo (0432, YELLOW, RED);
        new JustAmmo = JustAmmo (0433, YELLOW, BLUE);
        new JustAmmo = JustAmmo (0434, RED, BLUE);
        new JustAmmo = JustAmmo (0435, YELLOW, RED);
        new JustAmmo = JustAmmo (0436, YELLOW, BLUE);
        new JustAmmo = JustAmmo (0437, RED, BLUE);
     */
}
package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

public class PlasmaGun extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[3];

    public PlasmaGun(Color color, int weaponID, boolean isLoaded) {
        super( color, weaponID, isLoaded);
        yellowAmmoCost = 1;
        blueAmmoCost = 1;
        redAmmoCost = 0;
    }
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[2] = false;
        avaiableMethod[0] = false;
        avaiableMethod[1] = false;



        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[0] = true;

        if (isLoaded())
            avaiableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[2] = true;

        return avaiableMethod;

    }


}
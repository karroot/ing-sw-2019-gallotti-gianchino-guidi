package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public class MachineGun extends WeaponCard implements DoDamage, MoveTarget
{
    private boolean[] avaiableMethod = new boolean[3];

    public MachineGun( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        yellowAmmoCost = 0;
        blueAmmoCost = 1;
        redAmmoCost = 1;
    }

    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[1] = false;
        avaiableMethod[0] = false;
        avaiableMethod[2] = false;


        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[0] = true;

        if (isLoaded()&& player.getAmmoYellow()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[1] = true;
        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>0)
            avaiableMethod[2] = true;

        return avaiableMethod;

    }

    @Override
    public void doDamage(Player player, int quantity) {

    }

    public void moveTarget()
    {

    }

}
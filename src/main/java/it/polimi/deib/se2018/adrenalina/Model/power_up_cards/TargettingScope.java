package it.polimi.deib.se2018.adrenalina.Model.power_up_cards;
import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;


public class TargettingScope extends PowerUpCard implements DoDamage
{

    public TargettingScope(Color color, int idPU)
    {
        super(color, idPU);

    }

    /**
     * this method add a damage to target player
     * @param player the player that will get the extra damage
     */
    public void doDamage(Player player)
    {
       player.doDamage(TargettingScope.super.getPlayer().getColor());
    }

}
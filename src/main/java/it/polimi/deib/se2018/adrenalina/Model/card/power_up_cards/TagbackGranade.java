package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Color;

public class TagbackGranade extends PowerUpCard
{


    public TagbackGranade(Color color, int idPU)
    {
        super(color, idPU);

    }

    /**
     * this method add a mark to target player
     * @param player the player that will be marked
     */
    public void usePowerUp(Player player)
    {
        markTarget(player,1);
    }



}
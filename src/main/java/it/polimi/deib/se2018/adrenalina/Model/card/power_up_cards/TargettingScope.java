package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;
import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;


public class TargettingScope extends PowerUpCard
{

    public TargettingScope(Color color, int idPU)
    {
        super(color, idPU);

    }

    /**
     * this method add a damage to target player
     * @param player the player that will get the extra damage
     */
    public void usePowerUp(Player player)
    {
       doDamage(player,1);
    }

}
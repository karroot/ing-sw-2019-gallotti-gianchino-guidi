package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;
import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import static it.polimi.deib.se2018.adrenalina.Model.Color.*;


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
    public void usePowerUp(Player player) throws IllegalStateException
    {
        if (this.color==BLUE) {
            if (getPlayer().getAmmoBlue() < 1)
                throw new IllegalStateException("non hai abbastanza munizioni");
            doDamage(player, 1);
            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }
        if (this.color==RED){
            if(this.player.getAmmoRed()<1)
                throw new IllegalStateException("non hai abbastanza munizioni");
            doDamage(player,1);
            this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        }
        if (this.color==YELLOW){
            if(this.player.getAmmoYellow()<1)
                throw new IllegalStateException("non hai abbastanza munizioni");
            doDamage(player,1);
            this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
        }
    }
}
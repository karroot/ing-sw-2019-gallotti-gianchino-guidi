package it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.deib.se2018.adrenalina.Model.Color.*;


/**
 * This class implements the powerUp card Targetting Scoppe.
 *
 * @author Karroot
 */
public class TargettingScope extends PowerUpCard
{

    public TargettingScope(Color color, int idPU)
    {
        super(color, idPU);
        name = "Mirino";

    }

    public List<Color> check()
    {
        List<Color> colorList = new LinkedList<>();
         if (this.player.getAmmoYellow() > 0)
             colorList.add(YELLOW);
        if (this.player.getAmmoRed() > 0)
            colorList.add(RED);
        if (this.player.getAmmoBlue() > 0)
            colorList.add(BLUE);

        return colorList;
    }

    /**
     * this method add a damage to target player
     * @param colorPlayer the player that will get the extra damage
     * @param ammoColor the color of ammo to use for the payment
     * @throws IllegalStateException if there aren't enough blue ammo
     */
    public void usePowerUp(ColorId colorPlayer, Color ammoColor) throws IllegalStateException
    {
        if (ammoColor.equals(BLUE)) {
            if (getPlayer().getAmmoBlue() < 1)
                throw new IllegalStateException("non hai abbastanza munizioni");
            checkTerminator(colorPlayer);

            this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
        }
        if (ammoColor.equals(RED)){
            if(this.player.getAmmoRed()<1)
                throw new IllegalStateException("non hai abbastanza munizioni");
            checkTerminator(colorPlayer);

            this.player.setAmmoRed(this.player.getAmmoRed() - 1);
        }
        if (ammoColor.equals(YELLOW)){
            if(this.player.getAmmoYellow()<1)
                throw new IllegalStateException("non hai abbastanza munizioni");
            checkTerminator(colorPlayer);

            this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
        }
    }

    private void checkTerminator(ColorId colorPlayer) {
        if(this.player.getSquare().getGameBoard().isTerminatorMode() && colorPlayer.equals(ColorId.PURPLE))
        {

            doDamage(player.getSquare().getGameBoard().getTermi(),1);


        }
        else{
        doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayer)).collect(Collectors.toList()).get(0),1);}
    }
}
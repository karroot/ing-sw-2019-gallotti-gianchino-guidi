package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.Set;

public class StateSpecialMethods
{
    public static Set<Square> checkValidSquares (Player player, Set<Square> squareSet)
    {
        for (Square square : squareSet)
        {
            if (square.isAmmoPoint)
            {
                AmmoPoint ammopoint = (AmmoPoint) square;
                if ( ammopoint.getAmmoTiles() == null)
                    squareSet.remove(square);
            }

            if (square.isSpawnPoint)
            {
                SpawnPoint spawnPoint = (SpawnPoint) square;
                boolean validSquare = false;

                for (WeaponCard weaponCardIterate : spawnPoint.getWeaponCardList())
                {
                    if (checkGrabbable(weaponCardIterate, player))
                    {
                        validSquare = true;
                        break;
                    }
                }
                if (!validSquare)
                    squareSet.remove(square);
            }
        }

        return squareSet;
    }

    /**
     * check if player can grab a weapon
     * @param player player to check
     * @param weaponCard weapon to check if can be grab
     * @return true if it can be grab, false if not
     */
    public static boolean checkGrabbable( WeaponCard weaponCard, Player player)
    {
        if(weaponCard.getColor().equals(Color.BLUE))
            if(player.getAmmoBlue()>= weaponCard.getBlueAmmoCost()-1 && player.getAmmoYellow() >= weaponCard.getYellowAmmoCost() && player.getAmmoRed()>=weaponCard.getRedAmmoCost())
                return true;
        if(weaponCard.getColor().equals(Color.RED))
            if(player.getAmmoBlue()>= weaponCard.getBlueAmmoCost() && player.getAmmoYellow() >= weaponCard.getYellowAmmoCost() && player.getAmmoRed()>=weaponCard.getRedAmmoCost()-1)
                return true;
        if(weaponCard.getColor().equals(Color.YELLOW))
            if(player.getAmmoBlue()>= weaponCard.getBlueAmmoCost() && player.getAmmoYellow() >= weaponCard.getYellowAmmoCost()-1 && player.getAmmoRed()>=weaponCard.getRedAmmoCost())
                return true;
        return false;
    }


}

package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

public class provaMetodi {
    public void move (Player player, Square square)
    {
        player.setSquare(square);
    }

    public void grab (Player player, WeaponCard chosenWeapon)
    {
        if (player.getSquare().isAmmoPoint())
        {
            player.getSquare().getAmmoPoint().useAmmoTiles(player);
        }

        if (player.getSquare().isSpawnPoint())
        {
            player.getSquare().getSpawnPoint().drawWeapon(chosenWeapon);
        }
    }
}

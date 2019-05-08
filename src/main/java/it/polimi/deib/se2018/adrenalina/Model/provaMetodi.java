package it.polimi.deib.se2018.adrenalina.Model;

import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

public class provaMetodi {
    public void move (Player player, Square square)
    {
        player.setSquare(square);
    }

    public void grab (Player player, WeaponCard chosenWeapon)
    {
        if (player.getSquare().isAmmoPoint())
        {
            AmmoPoint ammoPoint = (AmmoPoint) player.getSquare();
            ammoPoint.useAmmoTiles(player);

        }

        if (player.getSquare().isSpawnPoint())
        {
            SpawnPoint spawnPoint = (SpawnPoint) player.getSquare();
            spawnPoint.drawWeapon(chosenWeapon);
        }
    }
}

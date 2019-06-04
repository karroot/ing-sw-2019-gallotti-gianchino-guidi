package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public class SquareImmutable implements Serializable
{
    private int x; //Coordinate x of square

    private int y; //Cordinate y of square

    protected boolean isAmmoPoint;

    protected boolean isSpawnPoint;

    protected ColorRoom color;

    private List<ColorId> playerList;

    //Only SpawnPoint
    private List<String> weaponCardList = null;

    //Only AmmoPoint
    private AmmoTilesImmutable ammoTiles = null;



    public SquareImmutable(Square square)
    {
        this.x = square.getX();
        this.y = square.getY();
        this.isAmmoPoint = square.isAmmoPoint();
        this.isSpawnPoint = square.isSpawnPoint();
        this.color = square.getColor();
        this.playerList = square.getPlayerListColor();

        if (square.isSpawnPoint())
        {
            SpawnPoint s = (SpawnPoint) square;
            weaponCardList = s.getWeaponCardList().stream().map(WeaponCard::getName).collect(Collectors.toList());
        }
        else
        {
            AmmoPoint ammoPoint = (AmmoPoint) square;

            if (ammoPoint.getAmmoTiles() instanceof JustAmmo)
            {
                JustAmmo justAmmo = (JustAmmo) ammoPoint.getAmmoTiles();
                ammoTiles = new JustAmmoImmutable(justAmmo.getAmmoCardID(),justAmmo.getSingleAmmo(),justAmmo.getDoubleAmmo());
            }
            else
            {
                PowerAndAmmo powerAndAmmo = (PowerAndAmmo) ammoPoint.getAmmoTiles();
                ammoTiles = new PowerAndAmmoImmutable(powerAndAmmo.getAmmoCardID(),powerAndAmmo.getSingleAmmo(),powerAndAmmo.getSecondSingleAmmo());
            }

        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAmmoPoint() {
        return isAmmoPoint;
    }

    public boolean isSpawnPoint() {
        return isSpawnPoint;
    }

    public ColorRoom getColor() {
        return color;
    }

    public List<ColorId> getPlayerList() {
        return playerList;
    }

    public List<String> getWeaponCardList() {
        return weaponCardList;
    }

    public AmmoTilesImmutable getAmmoTiles() {
        return ammoTiles;
    }


}

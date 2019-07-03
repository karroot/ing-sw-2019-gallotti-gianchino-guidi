package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represent a copy Immutable of a Square  coming from the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class SquareImmutable implements Serializable
{
    private int x; //Coordinate x of square

    private int y; //Coordinate y of square

    protected boolean isAmmoPoint;

    protected boolean isSpawnPoint;

    protected ColorRoom color;

    private List<ColorId> playerList;

    //Only SpawnPoint
    private List<String> weaponCardList = null;

    //Only AmmoPoint
    private AmmoTilesImmutable ammoTiles = null;


    /**
     * Create the copy immutable of a square
     * @param square square from the model
     */
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

                if (justAmmo == null)
                    ammoTiles = null;
                else
                    ammoTiles = new JustAmmoImmutable(justAmmo.getAmmoCardID(),justAmmo.getSingleAmmo(),justAmmo.getDoubleAmmo());
            }
            else
            {
                PowerAndAmmo powerAndAmmo = (PowerAndAmmo) ammoPoint.getAmmoTiles();
                if (powerAndAmmo == null)
                    ammoTiles = null;
                else
                    ammoTiles = new PowerAndAmmoImmutable(powerAndAmmo.getAmmoCardID(),powerAndAmmo.getSingleAmmo(),powerAndAmmo.getSecondSingleAmmo());
            }

        }
    }

    /**
     * Getter for coordinate x
     * @return coordinate x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for coordinate y
     * @return coordinate y
     */
    public int getY() {
        return y;
    }

    /**
     * Says if the square is ammo point or not
     * @return the square is ammo point or not
     */
    public boolean isAmmoPoint() {
        return isAmmoPoint;
    }

    /**
     * Says if the square is Spawn point or not
     * @return the square is Spawn point or not
     */
    public boolean isSpawnPoint() {
        return isSpawnPoint;
    }

    /**
     * Getter for the color of room
     * @return color of room
     */
    public ColorRoom getColor() {
        return color;
    }

    /**
     * Getter for the List of all players in the square(Only color)
     * @return List of all players in the square(Only color)
     */
    public List<ColorId> getPlayerList() {
        return playerList;
    }

    /**
     * Getter for the List of all WeaponCards in the square(Only name)
     * @return List of all WeaponCards in the square(Only name)
     */
    public List<String> getWeaponCardList() {
        return weaponCardList;
    }

    /**
     * Getter for the copy immutable of ammo tiles in the square
     * @return copy immutable of ammo tiles in the square
     */
    public AmmoTilesImmutable getAmmoTiles() {
        return ammoTiles;
    }


}

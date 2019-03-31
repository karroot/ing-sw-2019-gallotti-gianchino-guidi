package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import java.util.*;

/**
 * 
 */
public abstract class WeaponCard {

    /**
     * Default constructor
     */
    public WeaponCard() {
    }

    /**
     * 
     */
    protected Colour color;

    /**
     * 
     */
    protected int idWP;

    /**
     * 
     */
    protected unsigned int[] ammoCost;

    /**
     * 
     */
    protected boolean isLoaded;


    /**
     * 
     */
    public void basicEffect() {
        // TODO implement here
    }

}
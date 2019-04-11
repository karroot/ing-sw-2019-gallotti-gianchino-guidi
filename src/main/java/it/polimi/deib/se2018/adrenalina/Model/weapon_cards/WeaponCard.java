package it.polimi.deib.se2018.adrenalina.Model.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.*;

public abstract class WeaponCard extends Stack<WeaponCard> {

    String name;

    private Player player;

    protected Color color;

    protected int idWP;

    protected  int[] ammoCost;

    protected boolean isLoaded;


    public WeaponCard()
    {

    }


    public void basicEffect()
    {

    }

    public String getName()
    {
        return name;
    }
}
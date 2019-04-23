package it.polimi.deib.se2018.adrenalina.Model;


import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;

import java.util.List;
import java.util.Set;

public interface StatePlayer
{


    public Set<Square> lookForRunAround(Player player, GameBoard gameBoard);


    public Set<Square> lookForGrabStuff(Player player, GameBoard gameBoard);


    public List<Player> lookForShootPeople(Player player, GameBoard gameBoard);


    public List<WeaponCard> checkReload(Player player);



}
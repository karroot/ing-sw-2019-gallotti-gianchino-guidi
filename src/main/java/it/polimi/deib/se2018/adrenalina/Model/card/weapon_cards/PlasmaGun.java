package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;
import java.util.stream.Collectors;

public class PlasmaGun extends WeaponCard
{
    private boolean[] avaiableMethod = new boolean[4];
    /**
     * Create the card PlasmaGun
     * @param color color of weapon
     * @param weaponID Id of the card
     * @param isLoaded Indicates if the weapon is loaded or not
     * @exception NullPointerException if color is null
     */
    public PlasmaGun(Color color, int weaponID, boolean isLoaded) throws NullPointerException {
        super( color, weaponID, isLoaded);
        this.name = "PlasmaGun";
        redAmmoCost = 0;
        yellowAmmoCost = 1;
        blueAmmoCost = 1;

    }

    /**
     * Check which modes of the weapon can be used by player that has this weapon
     * @return array of booleans of size 2 the first represent the basic mode the second the alternative mode
     * @exception IllegalStateException if this card doesn't belong at a player
     */
    public boolean[] checkAvaliableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: "+ name + " non appartiene a nessun giocatore");

        avaiableMethod[2] = false;

        avaiableMethod[1] = false;

        avaiableMethod[0] = false;

        avaiableMethod[3] = false;




        if (isLoaded() && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[0] = true;


        if  (isLoaded()&& player.getAmmoBlue()>0 && player.playerThatSee(player.getSquare().getGameBoard()).size()>1)
            avaiableMethod[1] = true;
        if (isLoaded())
            avaiableMethod[2] = true;
        if(isLoaded() && (checkPhaseGlide().size()>1) )
            avaiableMethod[3] = true;
        return avaiableMethod;

    }
    /**
     * Return the list of all target available for using the basic mode of this weapon
     * @return all player that can be affected with the lock rifle in basic mode
     * @throws IllegalStateException
     */
    public List<Player> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvaliableMode()[0])
            throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

        List<Player> playerList = new LinkedList<>();
        for ( Player p :  player.playerThatSee(player.getSquare().getGameBoard()))
        {
            if(!p.equals(player))
                playerList.add(p);
        }



        return playerList;//Returns all targets


    }

    /**
     * It uses the basic mode of the PlasmaGun
     * @param player player affected by weapon
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    public void basicMode(Player player,String[] orderEffect,int x,int y) throws IllegalStateException, IllegalArgumentException, IllegalAccessException {
        int i = 0;
        boolean[] booleans = checkAvaliableMode();
        if (!checkAvaliableMode()[2])
            throw  new IllegalStateException("arma scarica");
        while (i < orderEffect.length)
        {
            if (orderEffect[i].equals("basic"))
            {
                if (!checkAvaliableMode()[0])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                doDamage(player,2);
            }
            if (orderEffect[i].equals("with phase glide") && checkTargetAfterMove(x,y).size()>0) {
                if (!checkAvaliableMode()[2])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                moveTarget(this.player, x, y);

            }
            if (orderEffect[i].equals("with charged shot") && booleans[2])
            {
                if (!checkAvaliableMode()[1])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");

                doDamage(player,1);
                this.player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }
        i++;
        }

        this.isLoaded = false;
    }


    /**
     * Calculate in which square a player can be moved using the basic mode
     * @return Set of all square corrects
     * @exception IllegalStateException if the basic mode can't be used
     */
    public List<String> checkPhaseGlide() throws IllegalStateException
    {
        if (!this.isLoaded())
            throw  new IllegalStateException("Modalità avanzata dell'arma: "+name+" non eseguibile");

        Square square = player.getSquare();

        Set<Square> squares = square.getGameBoard().getArena().squareReachableNoWall(square.getX(),square.getY(),2);//Obtain all the reachable square



        return squares.stream().map(Square::toStringCoordinates).collect(Collectors.toList());//Returns squares as a list of string);
    }
    /**
     * Return the list of all target available for using the "wmove" effect of this weapon
     * @return all player that can be affected with the Plasma gun  and the effect "with charged shot"
     * Return all possible players that can be targets also if the player will move in other square
     * @exception IllegalStateException if the effect can't be used
     */
    public List<Player> checkTargetBeforeMove () throws IllegalStateException
    {
        Player dummie = new Player(ColorId.BLUE,"a","a",false);
        List<Player> ListPlayerReach = new LinkedList();
        Set<Player> playerReachable = new HashSet<>();
        if (!isLoaded()) //check mode
            throw  new IllegalStateException("Modalità dell'arma: "+name+" non eseguibile");
        Set<Square> target=player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2); //Obtain all players that can be targets



        for(Square i : target)
        {
            dummie.setSquare(i);
            if  (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 0)
                playerReachable.addAll(dummie.playerThatSee(dummie.getSquare().getGameBoard()));
        }
         ListPlayerReach.addAll(playerReachable);//Returns all targets
        return ListPlayerReach;
    }

    /**
     * It say if a player can reach another player from starting to move from his position
     * @return List of reachable player
     * @throws IllegalStateException if the alternative mode can't be used
     */
    public List<String> checkSquareBeforeMove () throws IllegalStateException
    {
        Player dummie = new Player(ColorId.BLUE,"a","a",false);
        List<Square> ListSquareReach = new LinkedList();
        Set<Square> SquareReachable = new HashSet<>();
        if (!isLoaded()) //check mode
            throw  new IllegalStateException("Modalità dell'arma: "+name+" non eseguibile");
        Set<Square> target=player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2); //Obtain all players that can be targets



        for(Square i : target)
        {
            dummie.setSquare(i);
            if  (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 1)
            {
                   for (Player k :  dummie.playerThatSee(dummie.getSquare().getGameBoard()) )
                        SquareReachable.add(i);
            }
        }
        ListSquareReach.addAll(SquareReachable);//Returns all targets

        return ListSquareReach.stream().map(Square::toStringCoordinates).collect(Collectors.toList());//Returns squares as a list of string
    }

    /**
     * It say if a player can reach another player from a defined position decide by the phase glide
     * @param x player choice of movement x
     * @param y player choice of movement y
     * @return List of reachable player
     */
    public List<Player> checkTargetAfterMove(int x,int y)
    {
        Player dummie2 = new Player(ColorId.BLUE,"a","a",false);
        List<Player> ListPlayerReach = new LinkedList();
        Set<Player> playerReachable = new HashSet<>();
        try {
            dummie2.setSquare(this.getPlayer().getSquare().getGameBoard().getArena().getSquare(x,y));
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            System.out.println(squareNotInGameBoard);
        }
        if  (dummie2.playerThatSee(dummie2.getSquare().getGameBoard()).size() > 0)
            playerReachable.addAll(dummie2.playerThatSee(dummie2.getSquare().getGameBoard()));
        ListPlayerReach.addAll(playerReachable);//Returns all targets

       if (ListPlayerReach.contains(player))
            ListPlayerReach.remove(player);

       return ListPlayerReach;
    }
}
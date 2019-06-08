package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author giovanni
 */


public class RocketLauncher extends WeaponCard
{

    private boolean[] availableMethod = new boolean[4];
    private Player dummie = new Player(ColorId.BLUE,"a","a",false);


    public RocketLauncher( Color color, int weaponID, boolean isLoaded) {
        super(color, weaponID, isLoaded);
        this.name = "Rocket Launcher";
        yellowAmmoCost = 0;
        blueAmmoCost = 0;
        redAmmoCost = 2;
    }

    @Override
    public void useWeapon(ResponseInput responseMessage) {

    }

    //mod base: Deal 2 damage to 1 target you can see that is not on your square. Then you may move the target 1 square.

    //with rocket jump: Move 1 or 2 squares. This effect can be used either before or after the basic effect.

    //with fragmenting warhead: During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.

    //Notes: If you use the rocket jump before the basic effect, you consider only your new square when determining if a target is legal.
    // You can even move off a square so you can shoot someone on it.
    // If you use the fragmenting warhead, you deal damage to everyone on the target's square before you move the target – your target will take 3 damage total.


    public boolean[] checkAvailableMode() throws IllegalStateException
    {
        if (player == null)
            throw new IllegalStateException("Carta: " + name + " non appartiene a nessun giocatore");//If this card doesn't belong to any player, it launches an exception


        availableMethod[0] = false;//I suppose that the modes can't be used
        availableMethod[1] = false;
        availableMethod[2] = false;
        availableMethod[3] = false;

        if (isLoaded() && MethodsWeapons.areSquareISeeNotMineNotEmpty(player, (List<Square>) MethodsWeapons.squareThatSee(player)))
        {
                availableMethod[0] = true;

        }


        if (isLoaded() && availableMethod[0] && player.getAmmoBlue() > 0)
        {
            availableMethod[1] = true;
        }

        if (isLoaded() && availableMethod[0] && player.getAmmoYellow() > 0)
        {
            availableMethod[2] = true;
        }

        if(isLoaded() && (checkPhaseGlide().size()>1))
            availableMethod[3] = true;

        return availableMethod;

    }

    public List<ColorId> checkBasicMode() throws IllegalStateException
    {
        if (!checkAvailableMode()[0]) //check mode
            throw  new IllegalStateException("Modalità xxx dell'arma: "+name+" non eseguibile");

        Set<Player> playersTarget = player.playerThatSee(player.getSquare().getGameBoard()); //Obtain all players that can be seen
        List<ColorId> colorIdList = new ArrayList<>();

        playersTarget.remove(player);

        for (Player playerIterate : playersTarget)
            colorIdList.add(playerIterate.getColor());

        return colorIdList;//Returns all targets
    }

    public void basicMode (ColorId colorPlayerTarget , String[] orderEffect, String squareCoordinatesAsStringPlayertoMove, String squareCoordinatesAsStringTargetToMove) throws IllegalStateException {
        if (!checkAvailableMode()[0]) //check mode
            throw new IllegalStateException("Modalità xxx dell'arma: " + name + " non eseguibile");

        int xplayer = MethodsWeapons.getXFromString(squareCoordinatesAsStringPlayertoMove);
        int yplayer = MethodsWeapons.getYFromString(squareCoordinatesAsStringPlayertoMove);
        int xtarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);
        int ytarget = MethodsWeapons.getXFromString(squareCoordinatesAsStringTargetToMove);


        int i = 0;
        boolean[] booleans = checkAvailableMode();
        boolean rememberToMoveTarget = false;
        while (i < orderEffect.length)
        {
            if (orderEffect[i].equals("basic"))
            {
                if (!checkAvailableMode()[0])
                    throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

                doDamage(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0),2);
                rememberToMoveTarget = true;
            }
            if (orderEffect[i].equals("with rocket jump:") && booleans[1]) {
                if (!checkAvailableMode()[2])
                    throw  new IllegalStateException("Modalità xx dell'arma: "+name+" non eseguibile");

                moveTarget(this.player, xplayer, yplayer);
                player.setAmmoBlue(this.player.getAmmoBlue() - 1);
            }
            if (orderEffect[i].equals("with fragmenting warhead") && booleans[2])
            {
                if (!checkAvailableMode()[1])
                    throw  new IllegalStateException("Modalità basic dell'arma: "+name+" non eseguibile");
                for (Player playerIterate : player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0).getSquare().getPlayerList())
                {
                    doDamage(playerIterate,1);
                }
                this.player.setAmmoYellow(this.player.getAmmoYellow() - 1);
            }
            i++;

            if (rememberToMoveTarget)
                moveTarget(player.getSquare().getGameBoard().getAllPlayer().stream().filter(player1 -> player1.getColor().equals(colorPlayerTarget)).collect(Collectors.toList()).get(0), xtarget, ytarget);
        }

        this.isLoaded = false;
    }


    public List<ColorId> checkWithRocketJump ()
    {
        Set<Player> playerSet = checkPhaseGlide();
        List<ColorId> colorIdList = new ArrayList<>();
        for (Player playerIterate : playerSet)
            colorIdList.add(playerIterate.getColor());

        return colorIdList;
    }


    public List<ColorId> checkWithFragmentingWarhead ()
    {
        List<Player> playersTargetList;
        List<ColorId> colorIdList = new ArrayList<>();
        playersTargetList = player.getSquare().getPlayerList();
        playersTargetList.remove(player);

        for (Player playerIterate : playersTargetList)
            colorIdList.add(playerIterate.getColor());

        return colorIdList;
    }


    private Set<Player> checkPhaseGlide() throws IllegalStateException
    {
        Set<Player> playerReachable = new HashSet<>();
        if (!checkAvailableMode()[2]) //check mode
            throw  new IllegalStateException("Modalità dell'arma: "+name+" non eseguibile");
        Set<Square> target=player.getSquare().getGameBoard().getArena().squareReachableNoWall(player.getSquare().getX() , player.getSquare().getY(),2); //Obtain all players that can be targets



        for(Square i : target)
        {
            dummie.setSquare(i);
            if  (dummie.playerThatSee(dummie.getSquare().getGameBoard()).size() > 0)
                playerReachable.addAll(dummie.playerThatSee(dummie.getSquare().getGameBoard()));
        }
        return playerReachable;//Returns all targets

    }
}